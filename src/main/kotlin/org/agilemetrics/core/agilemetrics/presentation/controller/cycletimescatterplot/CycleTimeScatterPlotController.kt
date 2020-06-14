package org.agilemetrics.core.agilemetrics.presentation.controller.cycletimescatterplot

import org.agilemetrics.core.agilemetrics.business.service.CycleTimeScatterPlotService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class CycleTimeScatterPlotController(
        private val service: CycleTimeScatterPlotService
) {

    @GetMapping("/cycle-time-scatterplot")
    @CrossOrigin
    fun getCycleTimeScatterPlot() = service.getValues()

}
