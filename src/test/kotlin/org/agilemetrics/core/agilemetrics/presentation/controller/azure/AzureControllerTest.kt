package org.agilemetrics.core.agilemetrics.presentation.controller.azure

import org.agilemetrics.core.agilemetrics.AgileMetricsApplication
import org.agilemetrics.core.agilemetrics.infrastructure.repository.workitem.WorkItemRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [AgileMetricsApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
internal class AzureControllerTest {

    @Autowired
    private lateinit var  azureController:AzureController

    @Autowired
    private lateinit var workItemRepository:WorkItemRepository

    @Test
    fun test() {
        azureController.fillWorkItemsFromAzure()
        Assertions.assertEquals(1,1)
    }
}