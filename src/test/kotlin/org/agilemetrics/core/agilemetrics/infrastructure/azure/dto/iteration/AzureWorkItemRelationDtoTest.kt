package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.iteration

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemTarget
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AzureWorkItemRelationDtoTest {

    @Test
    fun shouldTestGetWorkItemIds() {
        // Given
        val azureWorkItemRelationDto: AzureWorkItemRelationDto = createAzureWorkItemRelationDto()

        // When
        val ids: List<Long> = azureWorkItemRelationDto.getWorkItemIds()

        // Then
        assertEquals(1, ids[0])
        assertEquals(2, ids[1])

    }

    private fun createAzureWorkItemRelationDto(): AzureWorkItemRelationDto {
        return AzureWorkItemRelationDto(workItemRelations = listOf(
                AzureWorkItemRelationDto.WorkItemRelation(target = AzureWorkItemTarget(id = 1, url = "url1")),
                AzureWorkItemRelationDto.WorkItemRelation(target = AzureWorkItemTarget(id = 2, url = "url2"))
        ))
    }
}