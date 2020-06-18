package org.agilemetrics.core.agilemetrics.presentation.controller.cycletimescatterplot

import org.agilemetrics.core.agilemetrics.infrastructure.repository.document.CycleTimeDocument
import java.time.LocalDate
import java.time.Period

data class CycleTimeScatterPlotOut(
        val cycleTimeInDays: Int,
        val completionDate: LocalDate) {

    companion object {
        fun from(domain: CycleTimeDocument) = CycleTimeScatterPlotOut(
                cycleTimeInDays = Period
                        .between(domain.startDate, domain.endDate)
                        .plusDays(1)
                        .days,
                completionDate = domain.endDate
        )
    }

}
