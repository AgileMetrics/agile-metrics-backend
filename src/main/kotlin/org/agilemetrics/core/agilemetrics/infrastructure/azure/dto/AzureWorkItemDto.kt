package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class AzureWorkItemDto(val count: Int, val value: List<AzureWorkItem>) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class AzureWorkItem(val id: Long, val fields: AzureWorkItemFields) {
        companion object {
            fun listToHashMap(workItems: List<AzureWorkItem>): HashMap<Long, AzureWorkItem> {
                val azureWorkItemsMap: HashMap<Long, AzureWorkItem> = hashMapOf()
                workItems.forEach { item -> azureWorkItemsMap[item.id] = item }
                return azureWorkItemsMap
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class AzureWorkItemFields(@JsonProperty("System.Title")
                                   val title: String,

                                   @JsonSerialize(using = ToStringSerializer::class)
                                   @JsonDeserialize(using = LocalDateTimeDeserializer::class)
                                   @JsonProperty("System.CreatedDate")
                                   val createdDate: LocalDateTime
    )
}

