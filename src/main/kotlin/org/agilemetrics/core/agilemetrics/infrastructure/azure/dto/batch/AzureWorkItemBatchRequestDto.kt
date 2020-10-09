package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch

data class AzureWorkItemBatchRequestDto(val ids: List<Long>) {
    val fields: List<String> = listOf("System.WorkItemType", "System.State", "System.Title", "System.CreatedDate")
}