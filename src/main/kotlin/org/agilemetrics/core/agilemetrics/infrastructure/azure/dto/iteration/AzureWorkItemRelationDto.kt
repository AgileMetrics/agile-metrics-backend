package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.iteration

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemTarget
import java.util.stream.Collectors

data class AzureWorkItemRelationDto(val workItemRelations: List<WorkItemRelation>) {
    data class WorkItemRelation(val target: AzureWorkItemTarget)

    fun getWorkItemIds(): List<Long> {
        return workItemRelations.stream()
                .map { workItemRelation -> workItemRelation.target.id }
                .collect(Collectors.toList())
    }
}
