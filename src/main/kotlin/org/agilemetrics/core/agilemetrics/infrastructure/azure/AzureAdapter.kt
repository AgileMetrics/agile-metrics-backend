package org.agilemetrics.core.agilemetrics.infrastructure.azure

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.azure.mapper.WorkItemMapper
import org.agilemetrics.core.agilemetrics.infrastructure.azure.services.AzureApiService
import org.agilemetrics.core.agilemetrics.infrastructure.azure.services.AzureWorkItemService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AzureAdapter(private val azureWorkItemService: AzureWorkItemService,
                   private val azureApiService: AzureApiService,
                   private val workItemMapper: WorkItemMapper) {

    fun retrieveAllWorkItemsWithDoneStatus(): Flux<WorkItem> {
        //Get all done azure work item
        val itemIds: Mono<List<Long>> = azureApiService.executeWorkItemQuery("Select [System.Id] From WorkItems Where [System.State] = 'Done'")
        return azureWorkItemService.retrieveAzureWorkItems(itemIds)
                .map { workItemMapper.fromAzureWorkItem(it) }
    }

    fun retrieveWorkItemFromCurrentIteration(): Flux<WorkItem> {
        //Get all azure work items of the current iteration
        val itemIds: Mono<List<Long>> = azureApiService.getCurrentIterationId()
                .flatMap { iterationId -> azureApiService.getWorkItemIdsByIterationId(iterationId) }

        return azureWorkItemService.retrieveAzureWorkItems(itemIds)
                .map { workItemMapper.fromAzureWorkItem(it) }
    }
}
