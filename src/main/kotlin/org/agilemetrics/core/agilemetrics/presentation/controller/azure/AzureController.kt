package org.agilemetrics.core.agilemetrics.presentation.controller.azure

import org.agilemetrics.core.agilemetrics.business.service.AzureService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
class AzureController(private val azureService: AzureService) {

    @PostMapping("/azure/work-items")
    @ResponseStatus(HttpStatus.CREATED)
    fun fillWorkItemsFromAzure() {
        azureService.populateDatabaseWithAzureCurrentIteration()
    }

}