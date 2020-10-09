package org.agilemetrics.core.agilemetrics.infrastructure.azure.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch.AzureWorkItemBatchResponseDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.update.AzureWorkItemUpdateInformationDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.mapper.AzureWorkItemMapper
import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureWorkItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(MockitoExtension::class)
internal class AzureWorkItemServiceTest {

    @Mock
    private lateinit var  azureApiService: AzureApiService

    private val azureWorkItemMapper: AzureWorkItemMapper = AzureWorkItemMapper()

    private lateinit var azureWorkItemService:AzureWorkItemService
    private val objectMapper = jacksonObjectMapper()

    @BeforeEach
    fun setup(){
        azureWorkItemService = AzureWorkItemService(azureApiService,azureWorkItemMapper)
    }
    @Test
    fun shouldTestRetrieveAzureWorkItems() {
        // Given
        val ids: Mono<List<Long>> =Mono.just(listOf(2L,7L))
        val azureWorkItemBatchResponseDto: AzureWorkItemBatchResponseDto = objectMapper.readValue(File("src/test/resources/__files/azure_post_work_items_batch_information_response_ok.json"), AzureWorkItemBatchResponseDto::class.java)
        val azureWorkItem2UpdateInformationDto: AzureWorkItemUpdateInformationDto = objectMapper.readValue(File("src/test/resources/__files/azure_get_work_item_2_update_information_response_ok.json"), AzureWorkItemUpdateInformationDto::class.java)
        val azureWorkItem7UpdateInformationDto: AzureWorkItemUpdateInformationDto = objectMapper.readValue(File("src/test/resources/__files/azure_get_work_item_7_update_information_response_ok.json"), AzureWorkItemUpdateInformationDto::class.java)

        Mockito.`when`(azureApiService.getWorkItemsBatchInformation(listOf(2,7))).thenReturn(Mono.just(azureWorkItemBatchResponseDto))
        Mockito.`when`(azureApiService.getWorkItemUpdateInformation(2L)).thenReturn(Mono.just(azureWorkItem2UpdateInformationDto))
        Mockito.`when`(azureApiService.getWorkItemUpdateInformation(7L)).thenReturn(Mono.just(azureWorkItem7UpdateInformationDto))
        // When
        // Then
        StepVerifier
                .create(azureWorkItemService.retrieveAzureWorkItems(ids))
                .assertNext { assertWorkItem1(it) }
                .assertNext { assertWorkItem2(it) }
                .expectComplete()
                .verify()
    }

    private fun assertWorkItem1(item: AzureWorkItem) {
        assertNull(item.id)
        assertEquals("Persistencia en MongoDB", item.name)
        assertEquals(parseDate("2020-06-08T06:41:42.823Z"), item.created)
        assertEquals(3, item.transitions.size)
        assertEquals(parseDate("2020-06-08T06:41:42.823Z"), item.transitions[0].date)
        assertEquals("To Do", item.transitions[0].column)
        assertEquals(parseDate("2020-06-08T06:41:45.400Z"), item.transitions[1].date)
        assertEquals("Doing", item.transitions[1].column)
        assertEquals(parseDate("2020-08-06T06:33:21.357Z"), item.transitions[2].date)
        assertEquals("Done", item.transitions[2].column)
    }

    private fun assertWorkItem2(item: AzureWorkItem) {
        assertNull(item.id)
        assertEquals("Calcular percentile (SLAs)", item.name)
        assertEquals(parseDate("2020-06-08T06:44:55.387Z"), item.created)
        assertEquals(3, item.transitions.size)
        assertEquals(parseDate("2020-06-08T06:44:55.387Z"), item.transitions[0].date)
        assertEquals("To Do", item.transitions[0].column)
        assertEquals(parseDate("2020-08-06T06:42:12.030Z"), item.transitions[1].date)
        assertEquals("Doing", item.transitions[1].column)
        assertEquals(parseDate("2020-08-06T06:49:41.353Z"), item.transitions[2].date)
        assertEquals("Done", item.transitions[2].column)
    }
    private fun parseDate(dateAsString: String): LocalDateTime {
        return LocalDateTime.parse(dateAsString, DateTimeFormatter.ISO_DATE_TIME)
    }
}