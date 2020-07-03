package org.agilemetrics.core.agilemetrics.presentation.controller.cycletimescatterplot

import org.agilemetrics.core.agilemetrics.business.domain.CycleTime
import java.time.LocalDate

data class CycleTimeScatterPlotOut(
        val cycleTimeInDays: Int,
        val completionDate: LocalDate) {

    companion object {
        fun from(domain: CycleTime) = CycleTimeScatterPlotOut(
                cycleTimeInDays = domain.cycleTimeInDays,
                completionDate = domain.completionDate
        )
    }

}
