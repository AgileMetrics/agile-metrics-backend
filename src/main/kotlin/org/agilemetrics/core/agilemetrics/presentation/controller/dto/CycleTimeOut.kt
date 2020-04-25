package org.agilemetrics.core.agilemetrics.presentation.controller.dto

import java.time.LocalDate

data class CycleTimeOut(val id: Long,
                        val startDate: LocalDate,
                        val endDate: LocalDate)