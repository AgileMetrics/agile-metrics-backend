package org.agilemetrics.core.agilemetrics.business.service

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.repository.workitem.WorkItemDocument
import org.agilemetrics.core.agilemetrics.infrastructure.repository.workitem.WorkItemRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class WorkItemService(
        private val workItemRepository: WorkItemRepository) {

    fun save(workItem: Mono<WorkItem>): Mono<WorkItem> {
        return workItemRepository
                .insert(workItem.map { WorkItemDocument.from(it) })
                .next()
                .map { WorkItem.from(it) }
    }

    fun bulk(workItems: Flux<WorkItem>): Mono<Void> {
        return workItemRepository.saveAll(workItems.map { WorkItemDocument.from(it) })
                .then()
    }

    fun drop() {
        workItemRepository
                .deleteAll()
                .subscribe()
    }

}
