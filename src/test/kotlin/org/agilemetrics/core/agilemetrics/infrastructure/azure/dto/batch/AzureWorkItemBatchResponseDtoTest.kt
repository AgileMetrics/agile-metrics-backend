package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch.AzureWorkItemBatchResponseDto.AzureWorkItemInformation
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch.AzureWorkItemBatchResponseDto.AzureWorkItemInformationFields
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import java.time.LocalDateTime
import java.util.HashMap

internal class AzureWorkItemBatchResponseDtoTest {
    companion object {
        private val CREATION_DATE = LocalDateTime.now()
    }

    @Test
    @DisplayName( " Given a AzureWorkItemBatchResponseDto "
            + " When invoke to getAzureWorkItemInformationAsMap "
            + " Then transform the internal list in a map "
            + " And is returned ")
    fun shouldTestListToHashMap() {
        // Given
        val azureWorkItemBatchResponseDto: AzureWorkItemBatchResponseDto = createAzureWorkItemBatchResponseDto()

        // When
        val itemsAsMap: HashMap<Long, AzureWorkItemInformation> = azureWorkItemBatchResponseDto.getAzureWorkItemInformationAsMap()

        // Then
        assertEquals(2, itemsAsMap.size)

        assertEquals(16, azureWorkItemBatchResponseDto.value[0].id)
        assertEquals(itemsAsMap[16], azureWorkItemBatchResponseDto.value[0])

        assertEquals(22, azureWorkItemBatchResponseDto.value[1].id)
        assertEquals(itemsAsMap[22], azureWorkItemBatchResponseDto.value[1])

    }

    private fun createAzureWorkItemBatchResponseDto(): AzureWorkItemBatchResponseDto {
        return AzureWorkItemBatchResponseDto(count = 2, value = listOf(
                AzureWorkItemInformation(id = 16, fields = AzureWorkItemInformationFields(title = "item 1", createdDate = CREATION_DATE)),
                AzureWorkItemInformation(id = 22, fields = AzureWorkItemInformationFields(title = "item 2", createdDate = CREATION_DATE.plusDays(1)))
        ))
    }
}