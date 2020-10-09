package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class AzureWorkItemBatchResponseDto(val count: Int, val value: List<AzureWorkItemInformation>) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class AzureWorkItemInformation(val id: Long, val fields: AzureWorkItemInformationFields)

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class AzureWorkItemInformationFields(@JsonProperty("System.Title")
                                              val title: String,

                                              @JsonSerialize(using = ToStringSerializer::class)
                                              @JsonDeserialize(using = LocalDateTimeDeserializer::class)
                                              @JsonProperty("System.CreatedDate")
                                              val createdDate: LocalDateTime
    )

    fun getAzureWorkItemInformationAsMap(): HashMap<Long, AzureWorkItemInformation> {
        val azureWorkItemsMapInformation: HashMap<Long, AzureWorkItemInformation> = hashMapOf()
        this.value.forEach { item -> azureWorkItemsMapInformation[item.id] = item }
        return azureWorkItemsMapInformation
    }
}

