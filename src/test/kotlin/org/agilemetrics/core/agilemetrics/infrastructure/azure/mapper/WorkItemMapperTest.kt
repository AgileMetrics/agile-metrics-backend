package org.agilemetrics.core.agilemetrics.infrastructure.azure.mapper

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureWorkItem
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class WorkItemMapperTest {
    private val workItemMapper: WorkItemMapper = WorkItemMapper()

    @Test
    fun shouldTestFromAzureWorkItem() {
        // Given
        val azureWorkItem: AzureWorkItem = createAzureWorkItem()

        // When
        val workItem: WorkItem =workItemMapper.fromAzureWorkItem(azureWorkItem)

        // Then
        assertEquals(azureWorkItem.name, workItem.name)
        assertEquals(azureWorkItem.created, workItem.created)
        assertEquals(azureWorkItem.id, workItem.id)
        assertEquals(2, workItem.transitions.size)
        assertEquals(azureWorkItem.transitions[1].date, workItem.transitions[0].date)
        assertEquals("WIP", workItem.transitions[0].column)
        assertEquals(azureWorkItem.transitions[2].date, workItem.transitions[1].date)
        assertEquals("DONE", workItem.transitions[1].column)
    }

    private fun createAzureWorkItem(): AzureWorkItem {
        return AzureWorkItem(id = null, name = "Calcular percentile (SLAs)", created = LocalDateTime.parse("2020-06-08T06:44:55.387"), transitions = listOf(
                AzureWorkItem.WorkItemTransition(column = "To Do", date = LocalDateTime.parse("2020-06-08T06:44:55.387")),
                AzureWorkItem.WorkItemTransition(column = "Doing", date = LocalDateTime.parse("2020-08-06T06:42:12.030")),
                AzureWorkItem.WorkItemTransition(column = "Done", date = LocalDateTime.parse("2020-08-06T06:49:41.353"))
        ))
    }
}