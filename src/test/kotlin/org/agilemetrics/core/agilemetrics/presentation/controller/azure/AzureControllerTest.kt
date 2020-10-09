package org.agilemetrics.core.agilemetrics.presentation.controller.azure

import org.agilemetrics.core.agilemetrics.AgileMetricsApplication
import org.agilemetrics.core.agilemetrics.infrastructure.repository.workitem.WorkItemDocument
import org.agilemetrics.core.agilemetrics.infrastructure.repository.workitem.WorkItemRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.test.StepVerifier
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [AgileMetricsApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
internal class AzureControllerTest {
    companion object {
        private const val WORK_ITEM_1_NAME = "Persistencia en MongoDB"
        private val WORK_ITEM_1_CREATED_DATE: LocalDateTime = LocalDateTime.parse("2020-06-08T06:41:42.823")
        private val WORK_ITEM_1_WIP_MODIFICATION_DATE: LocalDateTime = LocalDateTime.parse("2020-06-08T06:41:45.400")
        private val WORK_ITEM_1_DONE_MODIFICATION_DATE: LocalDateTime = LocalDateTime.parse("2020-08-06T06:33:21.357")

        private const val WORK_ITEM_2_NAME = "Calcular percentile (SLAs)"
        private val WORK_ITEM_2_CREATED_DATE: LocalDateTime = LocalDateTime.parse("2020-06-08T06:44:55.387")
        private val WORK_ITEM_2_WIP_MODIFICATION_DATE: LocalDateTime = LocalDateTime.parse("2020-08-06T06:42:12.030")
        private val WORK_ITEM_2_DONE_MODIFICATION_DATE: LocalDateTime = LocalDateTime.parse("2020-08-06T06:49:41.353")
    }

    @Autowired
    private lateinit var azureController: AzureController

    @Autowired
    private lateinit var workItemRepository: WorkItemRepository

    @Test
    @DisplayName(" Given an azureController "
            + " When invoke to fillWorkItemsFromAzure "
            + " Then return workItems getting the content from Azure "
            + " And save in the database these work items ")
    
    fun shouldTestFillWorkItemsFromAzure() {
        // When
        azureController.fillWorkItemsFromAzure().block();

        //Then
        StepVerifier
                .create(workItemRepository.findAll())
                .assertNext {
                    assertWorkItem(it)
                }
                .assertNext {
                    assertWorkItem(it)
                }
                .expectComplete()
                .verify()
    }

    //The items can be emitted in different order
    private fun assertWorkItem(it: WorkItemDocument) {
        if (it.name == WORK_ITEM_1_NAME) {
            assertWorkItem1(it)
        } else {
            assertWorkItem2(it)
        }
    }

    private fun assertWorkItem1(it: WorkItemDocument) {
        assertEquals(WORK_ITEM_1_NAME, it.name)
        assertEquals(WORK_ITEM_1_CREATED_DATE, it.created)
        assertEquals(2, it.transitions.size)
        assertEquals("WIP", it.transitions[0].column)
        assertEquals(WORK_ITEM_1_WIP_MODIFICATION_DATE, it.transitions[0].date)
        assertEquals("DONE", it.transitions[1].column)
        assertEquals(WORK_ITEM_1_DONE_MODIFICATION_DATE, it.transitions[1].date)
    }

    private fun assertWorkItem2(it: WorkItemDocument) {
        assertEquals(WORK_ITEM_2_NAME, it.name)
        assertEquals(WORK_ITEM_2_CREATED_DATE, it.created)
        assertEquals(2, it.transitions.size)
        assertEquals("WIP", it.transitions[0].column)
        assertEquals(WORK_ITEM_2_WIP_MODIFICATION_DATE, it.transitions[0].date)
        assertEquals("DONE", it.transitions[1].column)
        assertEquals(WORK_ITEM_2_DONE_MODIFICATION_DATE, it.transitions[1].date)
    }


}