package org.agilemetrics.core.agilemetrics.infrastructure.repository.workitem

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.business.domain.WorkItemTransition
import org.springframework.data.annotation.Id

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("workItem")
data class WorkItemDocument(@Id val id: String?,
                            val name: String,
                            val created: LocalDateTime,
                            val transitions: List<WorkItemTransitionDocument>) {

    companion object {
        fun from(domain: WorkItem) = WorkItemDocument(
                id = domain.id,
                name = domain.name,
                created = domain.created ?: LocalDateTime.now(),
                transitions = domain.transitions.map {
                    WorkItemTransitionDocument.from(it)
                }
        )
    }

}

data class WorkItemTransitionDocument(val column: String,
                                      val date: LocalDateTime) {

    companion object {
        fun from(domain: WorkItemTransition) = WorkItemTransitionDocument(
                column = domain.column,
                date = domain.date
        )
    }

}
