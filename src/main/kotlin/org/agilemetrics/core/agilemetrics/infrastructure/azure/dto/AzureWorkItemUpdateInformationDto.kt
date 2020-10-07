package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class AzureWorkItemUpdateInformationDto(val value: List<Item>) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Item(val workItemId: Long,
                    @JsonSerialize(using = ToStringSerializer::class)
                    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
                    val revisedDate: LocalDateTime,
                    val fields: Fields) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Fields(@JsonProperty("System.BoardColumn") val boardColumn: FieldValue?)

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class FieldValue(val oldValue: String?, val newValue: String?)

    companion object {
        fun listToHashMap(azureWorkItemUpdateInformationDtos: List<AzureWorkItemUpdateInformationDto>): HashMap<Long, AzureWorkItemUpdateInformationDto> {
            val updateItemMap: HashMap<Long, AzureWorkItemUpdateInformationDto> = hashMapOf()
            azureWorkItemUpdateInformationDtos.forEach { item ->
                updateItemMap.put(item.value[0].workItemId, item)
            }
            return updateItemMap
        }
    }
}