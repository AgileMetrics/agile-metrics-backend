package org.agilemetrics.core.agilemetrics.presentation.controller.percentile

data class PercentileOut(
        val percentile50InDays: Int,
        val percentile75InDays: Int,
        val percentile85InDays: Int,
        val percentile95InDays: Int)
