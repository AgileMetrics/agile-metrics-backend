package org.agilemetrics.core.agilemetrics.infrastructure.azure.services

import org.agilemetrics.core.agilemetrics.AgileMetricsApplication
import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureApiContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.test.StepVerifier
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [AgileMetricsApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
internal class AzureApiServiceTest {

    @Autowired
    private lateinit var azureApiContext:AzureApiContext

    @Autowired
    private lateinit var azureApiService: AzureApiService

    private val expectedWorkItemIds: List<Long> = listOf(2, 7)
    private val expectedIterationId = "d42eda64-9c72-4b1b-8b2a-8cfa7cb69e75"
    private val workItemId2: Long = 2

    @BeforeEach
    fun before(){
        azureApiContext.setContext("Actionable-Agile-Metrics",
                "Actionable Agile Metrics",
                "user",
                "password" )
    }


    @Test
    @DisplayName(" Given an azureApiService object "
            + " When invoke to getCurrentIterationId "
            + " Then call to azure endpoint and return the iterationId ")
    fun shouldTestGetCurrentIterationId() {
        StepVerifier
                .create(azureApiService.getCurrentIterationId())
                .expectNext(expectedIterationId)
                .expectComplete()
                .verify()
    }

    @Test
    @DisplayName(" Given an expectedIterationId object "
            + " When invoke to getWorkItemIdsByIterationId "
            + " Then call to azure endpoint and return the workItem Ids of that iteration ")
    fun shouldTestGetWorkItemIdsByIterationId() {

        StepVerifier
                .create(azureApiService.getWorkItemIdsByIterationId(expectedIterationId))
                .expectNext(expectedWorkItemIds)
                .expectComplete()
                .verify()
    }

    @Test
    @DisplayName(" Given a list of workItem id's "
            + " When invoke to getWorkItemsBatchInformation "
            + " Then call to azure endpoint and return the general information of these workItems ")
    fun shouldTestGetWorkItemsBatchInformation() {
        StepVerifier
                .create(azureApiService.getWorkItemsBatchInformation(expectedWorkItemIds))
                .assertNext {
                    assertEquals(2, it.value.size)
                    //Check the item 0 to verify the parse is correct
                    assertEquals(workItemId2, it.value[0].id)
                    assertEquals("Persistencia en MongoDB", it.value[0].fields.title)
                    assertEquals(LocalDateTime.parse("2020-06-08T06:41:42.823Z", DateTimeFormatter.ISO_DATE_TIME), it.value[0].fields.createdDate)
                }
                .expectComplete()
                .verify()
    }

    @Test
    @DisplayName(" Given a workItem id "
            + " When invoke to getWorkItemUpdateInformation "
            + " Then call to azure endpoint and return the update information of this workItem ")
    fun shouldTestGetWorkItemUpdateInformation() {
        StepVerifier
                .create(azureApiService.getWorkItemUpdateInformation(workitemId = workItemId2))
                .assertNext {
                    assertEquals(5, it.value.size)
                    //Check the item 1 to verify the parse is correct
                    assertEquals(workItemId2, it.value[0].workItemId)
                    assertEquals("To Do", it.value[1].fields.boardColumn!!.oldValue)
                    assertEquals("Doing", it.value[1].fields.boardColumn!!.newValue)
                }
                .expectComplete()
                .verify()
    }

    @Test
    @DisplayName(" Given a workItem query "
            + " When invoke to executeWorkItemQuery "
            + " Then call to azure endpoint and return the list of workItems affected ")
    fun shouldTestExecuteWorkItemQuery() {
        StepVerifier
                .create(azureApiService.executeWorkItemQuery("Select [System.Id] From WorkItems Where [System.State] = 'Done'"))
                .assertNext {
                    assertEquals(2, it.size)
                    assertEquals(2, it[0])
                    assertEquals(7, it[1])
                }
                .expectComplete()
                .verify()
    }
}