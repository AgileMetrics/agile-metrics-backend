package org.agilemetrics.core.agilemetrics.presentation.controller.azure

import org.agilemetrics.core.agilemetrics.business.service.AzureService
import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureApiContext
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
class AzureController(private val azureService: AzureService, private val azureApiContext: AzureApiContext) {

    @PostMapping("/azure/work-items")
    @ResponseStatus(HttpStatus.CREATED)
    fun fillWorkItemsFromAzure(@RequestBody azureRequestDto: AzureRequestDto): Mono<Void> {
        azureApiContext.setContext(azureRequestDto.organization, azureRequestDto.project, azureRequestDto.username, azureRequestDto.password)
        return azureService.populateDatabaseFromAzure()
    }
}