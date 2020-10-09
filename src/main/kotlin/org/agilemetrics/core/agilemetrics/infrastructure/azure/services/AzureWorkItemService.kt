package org.agilemetrics.core.agilemetrics.infrastructure.azure.services

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch.AzureWorkItemBatchResponseDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.update.AzureWorkItemUpdateInformationDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.mapper.AzureWorkItemMapper
import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureWorkItem
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AzureWorkItemService(private val azureApiService: AzureApiService, private val azureWorkItemMapper: AzureWorkItemMapper) {


    fun retrieveAzureWorkItems(itemIds: Mono<List<Long>>): Flux<AzureWorkItem> {

        //Get general information about each work item
        val azureWorkItemBatchResponseDtoMono: Mono<AzureWorkItemBatchResponseDto> = itemIds
                .flatMap { workItemIds -> azureApiService.getWorkItemsBatchInformation(workItemIds) }

        //Get update information about each work item
        val azureWorkItemUpdateInformationDtoFlux: Flux<AzureWorkItemUpdateInformationDto> = itemIds
                .flatMapMany { ids -> getAzureWorkItemUpdateInformation(ids) }

        //Generate the workitem with the info of the previous service
        return azureWorkItemBatchResponseDtoMono.flatMapMany { itemsInfo ->
            azureWorkItemUpdateInformationDtoFlux.map { updateInfo ->
                azureWorkItemMapper.mapToAzureWorkItem(itemsInfo, updateInfo)
            }
        }
    }

    private fun getAzureWorkItemUpdateInformation(ids: List<Long>): Flux<AzureWorkItemUpdateInformationDto> {
        return Flux.merge(ids.map { id -> azureApiService.getWorkItemUpdateInformation(id) })
    }
}