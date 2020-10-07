package org.agilemetrics.core.agilemetrics.infrastructure.azure

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemDto.AzureWorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemUpdateInformationDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.exception.AzureException
import org.agilemetrics.core.agilemetrics.infrastructure.azure.mapper.AzureWorkItemMapper
import org.agilemetrics.core.agilemetrics.infrastructure.azure.services.AzureInvoker
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Service
class AzureAdapter(private val azureInvoker: AzureInvoker, private val azureWorkItemMapper: AzureWorkItemMapper) {

    fun retrieveAzureWorkItems(): Mono<List<WorkItem>> {
        //Get azure work item id's of the current iteration
        val currentWorkItemIterationIds: Mono<List<Long>> = azureInvoker.getCurrentIterationId()
                .flatMap { iterationId -> azureInvoker.getWorkItemIdsByIterationId(iterationId) }

        //Get general information about each work item
        val azureWorkItemsMono: Mono<List<AzureWorkItem>> = currentWorkItemIterationIds
                .flatMap { workItemIds -> azureInvoker.getWorkItemsBatchInformation(workItemIds) }

        //Get update information about each work item
        val azureWorkItemUpdateInformationDtosMono: Mono<List<AzureWorkItemUpdateInformationDto>> = Flux
                .merge(currentWorkItemIterationIds.map { ids -> getAzureWorkItemUpdateInformation(ids) })
                .collectList()

        return Mono.zip(azureWorkItemsMono, azureWorkItemUpdateInformationDtosMono) {
            azureWorkItems, azureWorkItemUpdateInformationDtos -> getWorkItems(azureWorkItems, azureWorkItemUpdateInformationDtos)
        }

    }

    private fun getAzureWorkItemUpdateInformation(ids: List<Long>): Flux<AzureWorkItemUpdateInformationDto> {
        return Flux.merge(ids.map { id -> azureInvoker.getWorkItemUpdateInformation(id) })
    }

    private fun getWorkItems(azureWorkItems: List<AzureWorkItem>, azureWorkItemUpdateInformationDtos: List<AzureWorkItemUpdateInformationDto>): List<WorkItem> {
        val azureAzureWorkItemMap: HashMap<Long, AzureWorkItem> = AzureWorkItem.listToHashMap(azureWorkItems)
        val azureWorkItemUpdateInformationDtoMap: HashMap<Long, AzureWorkItemUpdateInformationDto> = AzureWorkItemUpdateInformationDto.listToHashMap(azureWorkItemUpdateInformationDtos)

        if (azureAzureWorkItemMap.keys != azureWorkItemUpdateInformationDtoMap.keys) {
            throw AzureException("Error preparing work item information from azure, keys are not the same")
        }

        return azureAzureWorkItemMap.keys
                .map { key ->
                    azureWorkItemMapper.mapToDomain(azureAzureWorkItemMap[key]!!, azureWorkItemUpdateInformationDtoMap[key]!!)
                }.toCollection(ArrayList())

    }
}
