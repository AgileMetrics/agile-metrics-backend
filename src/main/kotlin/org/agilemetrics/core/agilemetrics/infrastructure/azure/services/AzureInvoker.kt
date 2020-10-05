package org.agilemetrics.core.agilemetrics.infrastructure.azure.services

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.*
import org.agilemetrics.core.agilemetrics.infrastructure.azure.exception.AzureException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.stream.Collectors

@Service
class AzureInvoker(@Qualifier("azureWebClient") val webClient: WebClient) {

    /**
     * Get the current iteration Id
     * @return The internal Azure iteration ID
     */
    fun getCurrentIterationId(): Mono<String> {
        return webClient.get()
                .uri("/afeee19d-8d90-4249-9563-e3affd933c68/_apis/work/teamsettings/iterations?\$timeframe=current&api-version=6.0")
                .retrieve().bodyToMono(AzureIterationDto::class.java)
                .map { azureIterationDto -> getIterationId(azureIterationDto) }
    }

    /**
     * Get the internal Azure workitem Id's of a iteration
     * @param iterationId Internal Azure iteration ID
     * @return List of Azure internal work items id's
     */
    fun getWorkItemIdsByIterationId(iterationId: String): Mono<List<Int>> {
        return webClient.get()
                .uri("/afeee19d-8d90-4249-9563-e3affd933c68/_apis/work/teamsettings/iterations/$iterationId/workitems?api-version=6.0-preview.1")
                .retrieve().bodyToMono(AzureWorkItemRelation::class.java)
                .map { azureWorkItemRelation -> getWorkItemIds(azureWorkItemRelation) }
    }

    /**
     * This method is able to get information about a list of work items
     * @param workItemIds List of internal Azure work items Id's
     * @return A Dto with an internal list with information about each work item
     */
    fun getWorkItemsBatchInformation(workItemIds: List<Int>): Mono<List<AzureWorkItemDto.AzureWorkItem>> {
        return webClient.post()
                .uri("/_apis/wit/workitemsbatch?api-version=6.0")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(AzureWorkItemBatchRequestDto(workItemIds)), AzureWorkItemBatchRequestDto::class.java))
                .retrieve().bodyToMono(AzureWorkItemDto::class.java)
                .map {item -> item.value}
    }

    /**
     * This method return information about each time that a work item has been updated (For instance when the item has been moved in the kanban )
     * @param workitemId the internal Azure work item Id
     * @return A dto with the information requested
     */
    fun getWorkItemUpdateInformation(workitemId: Int): Mono<AzureWorkItemUpdateInformationDto> {
        return webClient.get()
                .uri("/afeee19d-8d90-4249-9563-e3affd933c68/_apis/wit/workItems/$workitemId/updates")
                .retrieve().bodyToMono(AzureWorkItemUpdateInformationDto::class.java)
    }


    private fun getIterationId(azureIterationDto: AzureIterationDto): String {
        if (azureIterationDto.count != 1) {
            throw AzureException("Current iteration should be contain only one iteration")
        }
        return azureIterationDto.value[0].id
    }

    private fun getWorkItemIds(azureWorkItemRelation: AzureWorkItemRelation): List<Int> {
        return azureWorkItemRelation.workItemRelations.stream()
                .map { workItemRelation -> workItemRelation.target.id }
                .collect(Collectors.toList())
    }

}
