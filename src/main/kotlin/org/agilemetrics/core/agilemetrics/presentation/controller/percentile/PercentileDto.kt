package org.agilemetrics.core.agilemetrics.presentation.controller.percentile

import org.agilemetrics.core.agilemetrics.business.domain.Percentile

data class PercentileOut(
        val percentile50InDays: Int,
        val percentile70InDays: Int,
        val percentile85InDays: Int,
        val percentile95InDays: Int) {

    companion object {
        fun from(domain: Percentile) = PercentileOut(
                percentile50InDays = domain.percentile50InDays,
                percentile70InDays = domain.percentile70InDays,
                percentile85InDays = domain.percentile85InDays,
                percentile95InDays = domain.percentile95InDays
        )
    }

}
