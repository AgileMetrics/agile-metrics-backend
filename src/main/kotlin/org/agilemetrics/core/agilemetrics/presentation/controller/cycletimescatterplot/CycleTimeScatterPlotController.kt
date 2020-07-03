package org.agilemetrics.core.agilemetrics.presentation.controller.cycletimescatterplot

import org.agilemetrics.core.agilemetrics.business.service.CycleTimeScatterPlotService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
class CycleTimeScatterPlotController(
        private val service: CycleTimeScatterPlotService) {

    @GetMapping("/cycle-time-scatterplot")
    @ResponseStatus(HttpStatus.OK)
    fun getCycleTimeScatterPlot(): Flux<CycleTimeScatterPlotOut> {
        return service
                .findAll()
                .map { CycleTimeScatterPlotOut.from(it) }
    }

}
