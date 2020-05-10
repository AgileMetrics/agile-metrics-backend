package org.agilemetrics.core.agilemetrics.presentation.controller.cycletimescatterplot

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.LocalDate
import java.time.Month

@RestController
@RequestMapping("/api/v1")
class CycleTimeScatterPlotController {

    val scatterPlotValues = listOf(
            CycleTimeScatterPlotOut(4, LocalDate.of(2020, Month.FEBRUARY, 10)),
            CycleTimeScatterPlotOut(21, LocalDate.of(2020, Month.MARCH, 2)),
            CycleTimeScatterPlotOut(6, LocalDate.of(2020, Month.FEBRUARY, 26)),
            CycleTimeScatterPlotOut(18, LocalDate.of(2020, Month.MARCH,2)),
            CycleTimeScatterPlotOut(15, LocalDate.of(2020, Month.MARCH,30)),
            CycleTimeScatterPlotOut(13, LocalDate.of(2020, Month.MARCH,31)),
            CycleTimeScatterPlotOut(12, LocalDate.of(2020, Month.APRIL,13)),
            CycleTimeScatterPlotOut(13, LocalDate.of(2020, Month.APRIL,13)),
            CycleTimeScatterPlotOut(63, LocalDate.of(2020, Month.MAY,4)),
            CycleTimeScatterPlotOut(8, LocalDate.of(2020, Month.MAY,5)),
            CycleTimeScatterPlotOut(14, LocalDate.of(2020, Month.MAY,5)),
            CycleTimeScatterPlotOut(15, LocalDate.of(2020, Month.MAY,5))
    )

    @GetMapping("/cycle-time-scatterplot")
    fun getCycleTimeScatterPlot() = Flux.just(scatterPlotValues)

}
