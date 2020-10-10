package org.agilemetrics.core.agilemetrics.infrastructure.azure.services

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch.AzureWorkItemBatchResponseDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch.AzureWorkItemBatchRequestDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.iteration.AzureIterationDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.iteration.AzureWorkItemRelationDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.query.AzureWorkItemQueryRequestDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.query.AzureWorkItemResponseQueryDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.update.AzureWorkItemUpdateInformationDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.exception.AzureException
import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureApiContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class AzureApiService(@Qualifier("azureWebClient") private val webClient: WebClient,
                      private val azureApiContext: AzureApiContext) {
    /**
     * Get the current iteration Id
     * @return The internal Azure iteration ID
     */
    fun getCurrentIterationId(): Mono<String> {

        return webClient.get()
                .uri("${azureApiContext.organization}/${azureApiContext.project}/_apis/work/teamsettings/iterations?\$timeframe=current&api-version=6.0")
                .headers { headers -> headers.setBasicAuth(azureApiContext.username!!, azureApiContext.password!!) }
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(AzureIterationDto::class.java)
                .map { getIterationId(it) }
    }

    /**
     * Get the internal Azure workitem Id's of a iteration
     * @param iterationId Internal Azure iteration ID
     * @return List of Azure internal work items id's
     */
    fun getWorkItemIdsByIterationId(iterationId: String): Mono<List<Long>> {
        return webClient.get()
                .uri("${azureApiContext.organization}/${azureApiContext.project}/_apis/work/teamsettings/iterations/$iterationId/workitems?api-version=6.0-preview.1")
                .headers { headers -> headers.setBasicAuth(azureApiContext.username!!, azureApiContext.password!!) }
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(AzureWorkItemRelationDto::class.java)
                .map { it.getWorkItemIds() }
    }

    /**
     * This method is able to get information about a list of work items
     * @param workItemIds List of internal Azure work items Id's
     * @return A Dto with an internal list with information about each work item
     */
    fun getWorkItemsBatchInformation(workItemIds: List<Long>): Mono<AzureWorkItemBatchResponseDto> {
        return webClient.post()
                .uri("${azureApiContext.organization}/${azureApiContext.project}/_apis/wit/workitemsbatch?api-version=6.0")
                .headers { headers -> headers.setBasicAuth(azureApiContext.username!!, azureApiContext.password!!) }
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(AzureWorkItemBatchRequestDto(workItemIds)), AzureWorkItemBatchRequestDto::class.java))
                .retrieve().bodyToMono(AzureWorkItemBatchResponseDto::class.java)
    }

    /**
     * This method return information about each time that a work item has been updated (For instance when the item has been moved in the kanban )
     * @param workitemId the internal Azure work item Id
     * @return A dto with the information requested
     */
    fun getWorkItemUpdateInformation(workitemId: Long): Mono<AzureWorkItemUpdateInformationDto> {
        return webClient.get()
                .uri("${azureApiContext.organization}/${azureApiContext.project}/_apis/wit/workItems/$workitemId/updates")
                .headers { headers -> headers.setBasicAuth(azureApiContext.username!!, azureApiContext.password!!) }
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(AzureWorkItemUpdateInformationDto::class.java)
    }

    /**
     * This method execute a query and return the work items affected by the query )
     * @param query the wiql query
     * @return A List of work items id
     */
    fun executeWorkItemQuery(query: String): Mono<List<Long>> {
        return webClient.post()
                .uri("${azureApiContext.organization}/${azureApiContext.project}/_apis/wit/wiql?api-version=6.0")
                .headers { headers -> headers.setBasicAuth(azureApiContext.username!!, azureApiContext.password!!) }
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(AzureWorkItemQueryRequestDto(query)), AzureWorkItemQueryRequestDto::class.java))
                .retrieve().bodyToMono(AzureWorkItemResponseQueryDto::class.java)
                .map { it.getWorkItemIds() }
    }

    private fun getIterationId(azureIterationDto: AzureIterationDto): String {
        if (azureIterationDto.count != 1) {
            throw AzureException("Current iteration should be contain only one iteration")
        }
        return azureIterationDto.value[0].id
    }


}
