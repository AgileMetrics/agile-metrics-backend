package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemDto.AzureWorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemDto.AzureWorkItemFields
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import java.time.LocalDateTime
import java.util.HashMap

internal class AzureWorkItemDtoTest {
    private val CREATION_DATE = LocalDateTime.now()

    @Test
    fun shouldTestListToHashMap() {
        // Given
        val itemsAsList: List<AzureWorkItem> = initAzureWorkItemList()

        // When
        val itemsAsMap: HashMap<Long, AzureWorkItem> = AzureWorkItem.listToHashMap(itemsAsList)

        // Then
        assertEquals(2, itemsAsMap.size)

        assertEquals(16, itemsAsList[0].id)
        assertEquals(itemsAsList[0], itemsAsMap[16])

        assertEquals(22, itemsAsList[1].id)
        assertEquals(itemsAsList[1], itemsAsMap[22])

    }

    private fun initAzureWorkItemList(): List<AzureWorkItem> {
        return listOf(
                AzureWorkItem(id = 16, fields = AzureWorkItemFields(title = "item 1", createdDate = CREATION_DATE)),
                AzureWorkItem(id = 22, fields = AzureWorkItemFields(title = "item 2", createdDate = CREATION_DATE.plusDays(1)))
        )


    }
}