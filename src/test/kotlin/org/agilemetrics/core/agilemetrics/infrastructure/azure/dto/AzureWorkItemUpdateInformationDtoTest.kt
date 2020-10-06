package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemUpdateInformationDto.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class AzureWorkItemUpdateInformationDtoTest {
    @Test
    fun shouldTestListToHashMap() {
        // Given
        val itemsAsList: List<AzureWorkItemUpdateInformationDto> = initAzureWorkItemUpdateInformationDtoList()

        // When
        val itemsAsMap:HashMap<Long, AzureWorkItemUpdateInformationDto> = AzureWorkItemUpdateInformationDto.listToHashMap(itemsAsList)

        // Then
        // Then
        Assertions.assertEquals(2, itemsAsMap.size)

        Assertions.assertEquals(16, itemsAsList[0].value[0].workItemId) //All the item in the internal value list must have the same id
        Assertions.assertEquals(itemsAsList[0], itemsAsMap[16])

        Assertions.assertEquals(22, itemsAsList[1].value[0].workItemId)//All the item in the internal value list must have the same id
        Assertions.assertEquals(itemsAsList[1], itemsAsMap[22])
    }

    private fun initAzureWorkItemUpdateInformationDtoList(): List<AzureWorkItemUpdateInformationDto> {
        val item1 = AzureWorkItemUpdateInformationDto(value = listOf(
                Item(workItemId = 16, revisedDate = LocalDateTime.now(), fields = Fields(FieldValue(oldValue = "To Do", newValue = "Doing"))))
        )
        val item2 = AzureWorkItemUpdateInformationDto(value = listOf(
                Item(workItemId = 22, revisedDate = LocalDateTime.now(), fields = Fields(FieldValue(oldValue = "To Do", newValue = "Doing"))))
        )
        return listOf(item1, item2)
    }
}