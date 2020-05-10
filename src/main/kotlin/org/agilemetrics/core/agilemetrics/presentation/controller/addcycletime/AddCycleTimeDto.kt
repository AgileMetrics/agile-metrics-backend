package org.agilemetrics.core.agilemetrics.presentation.controller.addcycletime

import java.time.LocalDate

data class CycleTimeIn(val startDate: LocalDate,
                       val endDate: LocalDate)

data class CycleTimeOut(val id: Long,
                        val startDate: LocalDate,
                        val endDate: LocalDate)
