package org.agilemetrics.core.agilemetrics.presentation.controller.cycletimescatterplot

import java.time.LocalDate

data class CycleTimeScatterPlotOut(
                        val cycleTimeInDays: Int,
                        val endDate: LocalDate)
