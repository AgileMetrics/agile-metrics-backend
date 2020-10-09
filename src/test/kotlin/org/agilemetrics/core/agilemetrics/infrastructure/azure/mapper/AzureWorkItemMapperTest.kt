package org.agilemetrics.core.agilemetrics.infrastructure.azure.mapper

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch.AzureWorkItemBatchResponseDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch.AzureWorkItemBatchResponseDto.AzureWorkItemInformation
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.update.AzureWorkItemUpdateInformationDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.exception.AzureException
import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureWorkItem
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


internal class AzureWorkItemMapperTest {
    private val azureWorkItemMapper: AzureWorkItemMapper = AzureWorkItemMapper()
    private val objectMapper = jacksonObjectMapper()

    @Test
    @DisplayName(" Given a AzureWorkItemBatchResponseDto object And azureWorkItemUpdateInformationDto object"
            + " And the id of the azureWorkItem in azureWorkItemUpdateInformationDto is contained in AzureWorkItemBatchResponseDto"
            + " When invoke to mapToAzureWorkItem "
            + " Then a azureWorkItem is created getting information from both objects ")
    fun shouldMapToDomain() {
        // Given
        val azureWorkItemBatchResponseDto: AzureWorkItemBatchResponseDto = objectMapper.readValue(File("src/test/resources/__files/azure_post_work_items_batch_information_response_ok.json"), AzureWorkItemBatchResponseDto::class.java)
        val azureWorkItemUpdateInformationDto: AzureWorkItemUpdateInformationDto = objectMapper.readValue(File("src/test/resources/__files/azure_get_work_item_7_update_information_response_ok.json"), AzureWorkItemUpdateInformationDto::class.java)
        val azureWorkItemInformationMap: HashMap<Long, AzureWorkItemInformation> = azureWorkItemBatchResponseDto.getAzureWorkItemInformationAsMap()
        val workItemId: Long = azureWorkItemUpdateInformationDto.getWorkItemId()

        // When
        val azureWorkItem: AzureWorkItem = azureWorkItemMapper.mapToAzureWorkItem(azureWorkItemBatchResponseDto, azureWorkItemUpdateInformationDto)

        // Then
        assertNull(azureWorkItem.id)
        assertEquals(azureWorkItemInformationMap[workItemId]!!.fields.createdDate, azureWorkItem.created)
        assertEquals(azureWorkItemInformationMap[workItemId]!!.fields.title, azureWorkItem.name)
        assertEquals(3, azureWorkItem.transitions.size)

        assertEquals("To Do", azureWorkItemUpdateInformationDto.value[0].fields.boardColumn!!.newValue!!)
        assertEquals("To Do", azureWorkItem.transitions[0].column)
        assertEquals(parseDate(azureWorkItemUpdateInformationDto.value[0].fields.createdDate!!.newValue!!), azureWorkItem.transitions[0].date)

        assertEquals("Doing", azureWorkItemUpdateInformationDto.value[3].fields.boardColumn!!.newValue!!)
        assertEquals("Doing", azureWorkItem.transitions[1].column)
        assertEquals(parseDate(azureWorkItemUpdateInformationDto.value[3].fields.changedDate!!.newValue!!), azureWorkItem.transitions[1].date)

        assertEquals("Done", azureWorkItemUpdateInformationDto.value[5].fields.boardColumn!!.newValue!!)
        assertEquals("Done", azureWorkItem.transitions[2].column)
        assertEquals(parseDate(azureWorkItemUpdateInformationDto.value[5].fields.changedDate!!.newValue!!), azureWorkItem.transitions[2].date)
    }

    @Test
    @DisplayName(" Given a AzureWorkItemBatchResponseDto object And azureWorkItemUpdateInformationDto object"
            + " And the id of the azureWorkItem in azureWorkItemUpdateInformationDto is NOT contained in AzureWorkItemBatchResponseDto"
            + " When invoke to mapToAzureWorkItem "
            + " Then an exception is thrown because the objects are not related and not is able to build an AzureWorkItem ")
    fun shouldFailIfBatchNotContainTheItem() {
        // Given
        val azureWorkItemBatchResponseDto = AzureWorkItemBatchResponseDto(count = 0, value = listOf())
        val azureWorkItemUpdateInformationDto: AzureWorkItemUpdateInformationDto = objectMapper.readValue(File("src/test/resources/__files/azure_get_work_item_7_update_information_response_ok.json"), AzureWorkItemUpdateInformationDto::class.java)

        // When
        //Then
        assertThrows(AzureException::class.java) {
            azureWorkItemMapper.mapToAzureWorkItem(azureWorkItemBatchResponseDto, azureWorkItemUpdateInformationDto)
        }
    }

    private fun parseDate(dateAsString: String): LocalDateTime {
        return LocalDateTime.parse(dateAsString, DateTimeFormatter.ISO_DATE_TIME)
    }
}