package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.query

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemTarget
import java.util.stream.Collectors

data class AzureWorkItemResponseQueryDto(val workItems: List<AzureWorkItemTarget>) {
    fun getWorkItemIds(): List<Long> {
        return workItems.stream()
                .map { azureWorkItemTarget -> azureWorkItemTarget.id }
                .collect(Collectors.toList())
    }
}