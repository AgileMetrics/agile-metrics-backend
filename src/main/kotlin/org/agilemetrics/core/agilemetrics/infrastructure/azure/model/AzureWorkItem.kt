package org.agilemetrics.core.agilemetrics.infrastructure.azure.model

import java.time.LocalDateTime

data class AzureWorkItem(val id: String?,
                    val name: String,
                    val created: LocalDateTime?,
                    val transitions: List<WorkItemTransition>) {

    data class WorkItemTransition(val column: String,
                                  val date: LocalDateTime)
}



