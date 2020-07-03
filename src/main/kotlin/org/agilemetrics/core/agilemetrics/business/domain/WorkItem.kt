package org.agilemetrics.core.agilemetrics.business.domain

import org.agilemetrics.core.agilemetrics.infrastructure.repository.workitem.WorkItemDocument
import org.agilemetrics.core.agilemetrics.infrastructure.repository.workitem.WorkItemTransitionDocument
import org.agilemetrics.core.agilemetrics.presentation.controller.addworkitem.AddWorkItemIn
import org.agilemetrics.core.agilemetrics.presentation.controller.addworkitem.AddWorkItemTransition
import java.time.LocalDateTime

data class WorkItem(val id: String?,
                    val name: String,
                    val created: LocalDateTime?,
                    val transitions: List<WorkItemTransition>) {

    companion object {
        fun from(json: AddWorkItemIn) = WorkItem(
                id = null,
                name = json.name,
                created = null,
                transitions = json.transitions
                        .map { WorkItemTransition.from(it) }
        )

        fun from(document: WorkItemDocument) = WorkItem(
                id = document.id,
                name = document.name,
                created = document.created,
                transitions = document.transitions
                        .map { WorkItemTransition.from(it) }
        )
    }

}

data class WorkItemTransition(val column: String,
                              val date: LocalDateTime) {

    companion object {
        fun from(json: AddWorkItemTransition) = WorkItemTransition(
                column = json.column,
                date = json.date
        )

        fun from(document: WorkItemTransitionDocument) = WorkItemTransition(
                column = document.column,
                date = document.date
        )

    }
}

