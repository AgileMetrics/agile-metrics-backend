package org.agilemetrics.core.agilemetrics.infrastructure.repository.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("cycleTime")
data class CycleTimeDocument(@Id val id: String,
                             val startDate: LocalDate,
                             val endDate: LocalDate,
                             val teamId: String)