package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.query

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemTarget
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class AzureWorkItemResponseQueryDtoTest {

    @Test
    @DisplayName( " Given a AzureWorkItemResponseQueryDto object "
            + " When invoke to getWorkItemIds "
            + " Then a list with the work items is returned ")
    fun shouldTestGetWorkItemIds() {
        // Given
        val azureWorkItemResponseQueryDto: AzureWorkItemResponseQueryDto = createAzureWorkItemResponseQueryDto()

        // When
        val ids: List<Long> = azureWorkItemResponseQueryDto.getWorkItemIds()

        // Then
        assertEquals(1, ids[0])
        assertEquals(2, ids[1])

    }

    private fun createAzureWorkItemResponseQueryDto(): AzureWorkItemResponseQueryDto {
        return AzureWorkItemResponseQueryDto(workItems = listOf(
                AzureWorkItemTarget(id = 1, url = "url1"),
                AzureWorkItemTarget(id = 2, url = "url2")
        ))

    }
}