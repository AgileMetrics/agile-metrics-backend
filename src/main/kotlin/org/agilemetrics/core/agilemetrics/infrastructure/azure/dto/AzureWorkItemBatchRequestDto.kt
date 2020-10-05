package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto

data class AzureWorkItemBatchRequestDto(val ids: List<Int>) {
    val fields: List<String> = listOf("System.WorkItemType", "System.State", "System.Title", "System.CreatedDate")
}