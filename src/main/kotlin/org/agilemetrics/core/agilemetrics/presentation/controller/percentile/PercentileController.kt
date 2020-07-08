package org.agilemetrics.core.agilemetrics.presentation.controller.percentile

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
class PercentileController {

    @GetMapping("/percentiles")
    @ResponseStatus(HttpStatus.OK)
    fun getCycleTimeScatterPlot(): Mono<PercentileOut> {
        return Mono.just(PercentileOut(11, 25, 45, 55))
    }

}
