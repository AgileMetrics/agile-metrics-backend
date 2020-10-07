package org.agilemetrics.core.agilemetrics.infrastructure.azure.services

import org.agilemetrics.core.agilemetrics.AgileMetricsApplication
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemUpdateInformationDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.test.StepVerifier
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [AgileMetricsApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
internal class AzureInvokerTest {

    @Autowired
    private lateinit var azureInvoker: AzureInvoker

    private val expectedWorkItemIds: List<Long> = listOf(1, 2)
    private val expectedIterationId = "d42eda64-9c72-4b1b-8b2a-8cfa7cb69e75"
    private val workItemId1: Long = 1

    @Test
    fun shouldTestGetCurrentIterationId() {

        StepVerifier
                .create(azureInvoker.getCurrentIterationId())
                .expectNext(expectedIterationId)
                .expectComplete()
                .verify()
    }

    @Test
    fun shouldTestGetWorkItemIdsByIterationId() {

        StepVerifier
                .create(azureInvoker.getWorkItemIdsByIterationId(expectedIterationId))
                .expectNext(expectedWorkItemIds)
                .expectComplete()
                .verify()
    }

    @Test
    fun shouldTestGetWorkItemsBatchInformation() {
        StepVerifier
                .create(azureInvoker.getWorkItemsBatchInformation(expectedWorkItemIds))
                .thenConsumeWhile { items -> assertBatchInformation(items) }
                .expectComplete()
                .verify()
    }

    @Test
    fun shouldTestGetWorkItemUpdateInformation() {
        StepVerifier
                .create(azureInvoker.getWorkItemUpdateInformation(workitemId = workItemId1))
                .thenConsumeWhile { item -> assertUpdateInformation(item) }
                .expectComplete()
                .verify()
    }

    private fun assertUpdateInformation(item: AzureWorkItemUpdateInformationDto): Boolean {
        assertEquals(4, item.value.size)
        //Check the item 1 to verify the parse is correct
        assertEquals(workItemId1, item.value[1].workItemId)
        assertEquals(LocalDateTime.parse("2020-06-08T06:41:45.703Z", DateTimeFormatter.ISO_DATE_TIME), item.value[1].revisedDate)
        assertEquals("To Do", item.value[1].fields.boardColumn!!.oldValue)
        assertEquals("Doing", item.value[1].fields.boardColumn!!.newValue)
        return true
    }


    private fun assertBatchInformation(items: List<AzureWorkItemDto.AzureWorkItem>): Boolean {
        assertEquals(2, items.size)
        //Check the item 0 to verify the parse is correct
        assertEquals(workItemId1, items[0].id)
        assertEquals("PoC Montecarlo", items[0].fields.title)
        assertEquals(LocalDateTime.parse("2020-06-08T06:41:28.61Z", DateTimeFormatter.ISO_DATE_TIME), items[0].fields.createdDate)
        return true
    }


}