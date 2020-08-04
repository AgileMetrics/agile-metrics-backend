package org.agilemetrics.core.agilemetrics.presentation.controller.percentile

import org.agilemetrics.core.agilemetrics.business.service.PercentileService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
class PercentileController(
        private val service: PercentileService) {

    @GetMapping("/percentiles")
    @ResponseStatus(HttpStatus.OK)
    fun getCycleTimeScatterPlot(): Mono<PercentileOut> {
        return service.getPercentiles()
    }

}
