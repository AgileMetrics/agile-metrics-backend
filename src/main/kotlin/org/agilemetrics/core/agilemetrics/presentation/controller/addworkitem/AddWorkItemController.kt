package org.agilemetrics.core.agilemetrics.presentation.controller.addworkitem

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.business.service.WorkItemService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
class AddWorkItemController(private val workItemService: WorkItemService) {

    @PostMapping("/work-item")
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody addWorkItemIn: Mono<AddWorkItemIn>): Mono<AddWorkItemOut> {
        return workItemService
                .save(addWorkItemIn.map { WorkItem.from(it) })
                .map { AddWorkItemOut.from(it) }
    }

}