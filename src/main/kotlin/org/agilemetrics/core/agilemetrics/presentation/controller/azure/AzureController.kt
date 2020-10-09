package org.agilemetrics.core.agilemetrics.presentation.controller.azure

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.business.service.AzureService
import org.agilemetrics.core.agilemetrics.infrastructure.azure.AzureAdapter
import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureApiContext
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
class AzureController(private val azureService: AzureService, private val azureAdapter: AzureAdapter, private val azureApiContext: AzureApiContext) {

    @PostMapping("/azure/work-items")
    @ResponseStatus(HttpStatus.CREATED)
    fun fillWorkItemsFromAzure():Mono<Void> {
        return azureService.populateDatabaseFromAzure()
    }

    @PostMapping("/azure/test1")
    fun test1(@RequestBody azureRequestDto: Mono<AzureRequestDto>): Flux<WorkItem> {

        //azureApiContext.setContext(azureRequestDto.organization, azureRequestDto.project,azureRequestDto.username,azureRequestDto.password)
        return azureAdapter.retrieveAllWorkItemsWithDoneStatus()
    }

    @GetMapping("/azure/test2")
    fun test2(): Flux<WorkItem> {
        return azureAdapter.retrieveWorkItemFromCurrentIteration()
    }
}