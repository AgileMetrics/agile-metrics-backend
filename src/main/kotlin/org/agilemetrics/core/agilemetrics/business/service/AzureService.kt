package org.agilemetrics.core.agilemetrics.business.service

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.azure.AzureAdapter
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AzureService(private val azureAdapter: AzureAdapter,
                   private val workItemService: WorkItemService) {

    fun populateDatabaseFromAzure():Mono<Void> {
        val workItems: Flux<WorkItem> = azureAdapter.retrieveAllWorkItemsWithDoneStatus()
        return workItemService.bulk(workItems)
    }
}
