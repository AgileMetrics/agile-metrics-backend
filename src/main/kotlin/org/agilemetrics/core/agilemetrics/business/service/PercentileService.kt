package org.agilemetrics.core.agilemetrics.business.service

import org.agilemetrics.core.agilemetrics.business.domain.CycleTime
import org.agilemetrics.core.agilemetrics.business.domain.Percentile
import org.agilemetrics.core.agilemetrics.presentation.controller.percentile.PercentileOut
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import kotlin.math.ceil

@Service
class PercentileService(private val cycleTimeScatterPlotService: CycleTimeScatterPlotService) {

    fun getPercentiles(): Mono<PercentileOut> {
        return cycleTimeScatterPlotService
                .findAll()
                .sort { cycleTime1, cycleTime2 -> cycleTime1.cycleTimeInDays.compareTo(cycleTime2.cycleTimeInDays) }
                .collectList()
                .map { calculatePercentiles(it) }
                .map { PercentileOut.from(it) }
    }

    private fun calculatePercentiles(cycleTimes: List<CycleTime>): Percentile {
        return Percentile(
                calculatePercentile(cycleTimes, 50),
                calculatePercentile(cycleTimes, 70),
                calculatePercentile(cycleTimes, 85),
                calculatePercentile(cycleTimes, 95)
        )
    }

    private fun calculatePercentile(cycleTimes: List<CycleTime>, percentile: Int): Int {
        val index = ceil(percentile / 100.0 * cycleTimes.size).toInt()
        return cycleTimes[index - 1].cycleTimeInDays
    }


}
