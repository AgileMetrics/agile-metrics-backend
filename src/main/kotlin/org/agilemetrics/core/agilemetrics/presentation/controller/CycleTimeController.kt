package org.agilemetrics.core.agilemetrics.presentation.controller

import org.agilemetrics.core.agilemetrics.presentation.controller.dto.CycleTimeIn
import org.agilemetrics.core.agilemetrics.presentation.controller.dto.CycleTimeOut
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class CycleTimeController() {

    @PostMapping("/cycle-time")
    fun saveCycleTime(cycleTime: CycleTimeIn): CycleTimeOut? = null

}
