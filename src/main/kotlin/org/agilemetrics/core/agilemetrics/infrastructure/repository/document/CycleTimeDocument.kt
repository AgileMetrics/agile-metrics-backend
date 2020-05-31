package org.agilemetrics.core.agilemetrics.infrastructure.repository.document

import org.agilemetrics.core.agilemetrics.business.domain.CycleTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("cycleTime")
data class CycleTimeDocument(@Id val id: String?,
                             val startDate: LocalDate,
                             val endDate: LocalDate,
                             val teamId: String?) {

    companion object {
        fun from(domain: CycleTime) = CycleTimeDocument(
                id = domain.id,
                startDate = domain.startDate,
                endDate = domain.endDate,
                teamId = domain.teamId)
    }

}
