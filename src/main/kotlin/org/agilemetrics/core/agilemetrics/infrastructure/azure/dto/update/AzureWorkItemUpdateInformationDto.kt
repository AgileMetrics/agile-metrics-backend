package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.update

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
                    val fields: Fields) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Fields(@JsonProperty("System.CreatedDate") val createdDate: FieldValue?,
                      @JsonProperty("System.BoardColumn") val boardColumn: FieldValue?,
                      @JsonProperty("System.ChangedDate") val changedDate: FieldValue?)



    @JsonIgnoreProperties(ignoreUnknown = true)
    data class FieldValue(val oldValue: String?, val newValue: String?)

    fun getWorkItemId(): Long{
        return value[0].workItemId //Get the first item but all the item must have the same workItemId
    }

}