package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class AzureWorkItemDto(val count: Int, val value: List<AzureWorkItem>) {
    data class AzureWorkItem(val id: Long, val fields: AzureWorkItemFields){
        companion object {
            fun toHashMap(workItems: List<AzureWorkItem>): HashMap<Long, AzureWorkItem> {
                val azureWorkItemsMap: HashMap<Long, AzureWorkItem> = hashMapOf()
                workItems.forEach { item -> azureWorkItemsMap[item.id] = item }
                return azureWorkItemsMap
            }
        }
    }

    data class AzureWorkItemFields(@JsonProperty("System.Title") val title: String,
                                   @JsonProperty("System.CreatedDate") val createdDate: LocalDateTime
    )
}

