package org.agilemetrics.core.agilemetrics.infrastructure.azure.mapper

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemDto.AzureWorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemUpdateInformationDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.exception.AzureException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File


internal class AzureWorkItemMapperTest {
    private val azureWorkItemMapper:AzureWorkItemMapper = AzureWorkItemMapper()
    private val mapper = jacksonObjectMapper()

    @Test
    fun shouldMapToDomain() {
        // Given
        val azureWorkItemDto: AzureWorkItemDto = mapper.readValue(File("src/test/resources/__files/azure_get_work_items_batch_information_response_ok.json"), AzureWorkItemDto::class.java)
        val azureWorkItemUpdateInformationDto: AzureWorkItemUpdateInformationDto = mapper.readValue(File("src/test/resources/__files/azure_get_work_item_1_update_information_response_ok.json"), AzureWorkItemUpdateInformationDto::class.java)
        val azureWorkItem: AzureWorkItem =azureWorkItemDto.value[0]

        // When
        val workItem: WorkItem =azureWorkItemMapper.mapToDomain(azureWorkItem,azureWorkItemUpdateInformationDto)

        // Then
        assertEquals(azureWorkItem.id, azureWorkItemUpdateInformationDto.value[0].workItemId)
        assertNull(workItem.id)
        assertEquals(azureWorkItem.fields.createdDate,workItem.created)
        assertEquals(azureWorkItem.fields.title,workItem.name)
        assertEquals(2,workItem.transitions.size)
        assertEquals(azureWorkItemUpdateInformationDto.value[0].revisedDate,workItem.transitions[0].date)
        assertEquals("To Do",workItem.transitions[0].column)
        assertEquals(azureWorkItemUpdateInformationDto.value[1].revisedDate,workItem.transitions[1].date)
        assertEquals("Doing",workItem.transitions[1].column)

    }

    @Test
    fun shouldNotMapToDomainIfWorkItemAndUpdateInformationBelongToDifferentItems() {
        // Given
        val azureWorkItemDto: AzureWorkItemDto = mapper.readValue(File("src/test/resources/__files/azure_get_work_items_batch_information_response_ok.json"), AzureWorkItemDto::class.java)
        val azureWorkItemUpdateInformationDto: AzureWorkItemUpdateInformationDto = mapper.readValue(File("src/test/resources/__files/azure_get_work_item_1_update_information_response_ok.json"), AzureWorkItemUpdateInformationDto::class.java)
        val azureWorkItem: AzureWorkItem =azureWorkItemDto.value[1]//This item is different to the informationDto

        // When
        //Then
        assertThrows(AzureException::class.java) {
            assertNotEquals(azureWorkItem.id, azureWorkItemUpdateInformationDto.value[0].workItemId)
            azureWorkItemMapper.mapToDomain(azureWorkItem,azureWorkItemUpdateInformationDto)
        }
    }


}