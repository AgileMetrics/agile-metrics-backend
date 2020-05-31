package org.agilemetrics.core.agilemetrics.business.domain

import java.time.LocalDate

class CycleTime(val id: String? = null,
                val startDate: LocalDate,
                val endDate: LocalDate,
                val teamId: String? = null)
