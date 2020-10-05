package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class AzureWorkItemUpdateInformationDto(val value: List<Item>) {

    data class Item(val workItemId: Long,
                    val revisedDate: LocalDateTime,
                    val fields: Fields) {
    }

    data class Fields(@JsonProperty("System.BoardColumn") val boardColumn: FieldValue?)
    data class FieldValue(val oldValue: String?, val newValue: String?)

    companion object {
        fun toHashMap(azureWorkItemUpdateInformationDtos: List<AzureWorkItemUpdateInformationDto>): HashMap<Long, AzureWorkItemUpdateInformationDto> {
            val updateItemMap: HashMap<Long, AzureWorkItemUpdateInformationDto> = hashMapOf()
            azureWorkItemUpdateInformationDtos.forEach { item ->
                updateItemMap.put(item.value[0].workItemId, item)
            }
            return updateItemMap
        }
    }
}