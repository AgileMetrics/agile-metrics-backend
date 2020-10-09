package org.agilemetrics.core.agilemetrics.infrastructure.azure.mapper

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.business.domain.WorkItemTransition
import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureWorkItem
import org.springframework.stereotype.Service

@Service
class WorkItemMapper {
    fun fromAzureWorkItem(azureWorkItem: AzureWorkItem): WorkItem {
        return WorkItem(id = azureWorkItem.id, name = azureWorkItem.name, created = azureWorkItem.created,
                transitions = mapTransitions(azureWorkItem))
    }
    //Hack to work with the current domain workitem
    private fun mapTransitions(azureWorkItem: AzureWorkItem): List<WorkItemTransition> {
        return azureWorkItem.transitions
                .filter { it.column == "Doing" || it.column == "Done" }
                .map { WorkItemTransition(mapColumn(it.column), it.date) }
    }

    private fun mapColumn(column: String): String {
        return when (column) {
            "Doing" -> "WIP"
            "Done" -> "DONE"
            else -> column
        }
    }

}