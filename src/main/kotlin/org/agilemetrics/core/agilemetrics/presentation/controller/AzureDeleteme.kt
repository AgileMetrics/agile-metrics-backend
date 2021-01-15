package org.agilemetrics.core.agilemetrics.presentation.controller

//import org.agilemetrics.core.agilemetrics.infrastructure.azure.AzureAdapter
import org.agilemetrics.core.agilemetrics.infrastructure.azure.AzureAdapter
import org.agilemetrics.core.agilemetrics.infrastructure.azure.services.AzureInvoker
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class AzureDeleteme(
        private val azureAdapter: AzureAdapter,
        private val azureInvoker: AzureInvoker
) {

//    @GetMapping("/azure-test")
//    @CrossOrigin
//    fun getAzureWorkItem() = azureAdapter.getWorkItem(11)
//
//    @GetMapping("/azure-update-information")
//    @CrossOrigin
//    fun getAzureWorkItemUpdateInformation() = azureAdapter.getWorkItem(11)

    @GetMapping("/azure-iteration")
    @CrossOrigin
    fun getAzureIteration() = azureAdapter.retrieveAzureWorkItems()

}
