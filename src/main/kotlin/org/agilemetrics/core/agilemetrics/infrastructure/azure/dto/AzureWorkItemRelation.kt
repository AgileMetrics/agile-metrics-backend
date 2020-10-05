package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto

data class AzureWorkItemRelation(val workItemRelations: List<WorkItemRelation>) {
    data class WorkItemRelation(val target: WorkItemTarget)
    data class WorkItemTarget(val id: Int, val url: String)
}
