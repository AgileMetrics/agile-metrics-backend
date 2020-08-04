package org.agilemetrics.core.agilemetrics.business.domain

data class Percentile(val percentile50InDays: Int,
                      val percentile70InDays: Int,
                      val percentile85InDays: Int,
                      val percentile95InDays: Int)
