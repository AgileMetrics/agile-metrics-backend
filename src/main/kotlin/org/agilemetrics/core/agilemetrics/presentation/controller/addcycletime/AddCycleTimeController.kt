package org.agilemetrics.core.agilemetrics.presentation.controller.addcycletime

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class AddCycleTimeController() {

    @PostMapping("/cycle-time")
    fun saveCycleTime(cycleTime: CycleTimeIn): CycleTimeOut? = null

}
