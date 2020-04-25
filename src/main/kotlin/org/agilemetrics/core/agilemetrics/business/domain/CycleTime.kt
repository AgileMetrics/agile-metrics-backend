package org.agilemetrics.core.agilemetrics.business.domain

import java.time.LocalDate

data class CycleTime(val id: Long,
                     val startDate: LocalDate,
                     val endDate: LocalDate)
