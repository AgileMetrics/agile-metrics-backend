package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto

data class AzureWorkItemRelationDto(val workItemRelations: List<WorkItemRelation>) {
    data class WorkItemRelation(val target: WorkItemTarget)
    data class WorkItemTarget(val id: Long, val url: String)
}
