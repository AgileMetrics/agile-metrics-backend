package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.query

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemTarget
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AzureWorkItemResponseQueryDtoTest {
    @Test
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