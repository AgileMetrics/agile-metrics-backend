package org.agilemetrics.core.agilemetrics.business.domain

import java.time.LocalDate

data class CycleTime(val cycleTimeInDays: Int,
                val completionDate: LocalDate)
