package org.agilemetrics.core.agilemetrics.business.service

import org.agilemetrics.core.agilemetrics.business.domain.CycleTime
import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.business.domain.WorkItemTransition
import org.agilemetrics.core.agilemetrics.infrastructure.repository.workitem.WorkItemRepository

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.logging.Logger.getLogger

@Service
class CycleTimeScatterPlotService(
        private val workItemRepository: WorkItemRepository
) {
    companion object {
        private val loggerWithExplicitClass
                = getLogger("CycleTimeScatterPlotService::class.java")
    }

    fun findAll(): Flux<CycleTime> {
        return workItemRepository
                .findAll()
                .map { WorkItem.from(it) }
                .map { buildCycleTime(it) }
    }

    private fun buildCycleTime(workItem: WorkItem): CycleTime {
        loggerWithExplicitClass.info("WorkItem received: $workItem")
        return CycleTime(calculateCycleTimeInDays(workItem.transitions),
                LocalDate.from(getTransitionColumnDate(workItem.transitions, "DONE"))
        )
    }

    private fun calculateCycleTimeInDays(transitions: List<WorkItemTransition>): Int {
        return ChronoUnit.DAYS
                .between(
                        getTransitionColumnDate(transitions, "WIP"),
                        getTransitionColumnDate(transitions, "DONE"))
                .toInt()
                .plus(1)
    }

    private fun getTransitionColumnDate(transitions: List<WorkItemTransition>, column: String): LocalDateTime? {
        val transition: WorkItemTransition? = transitions.find { column == it.column }
        return transition?.date
    }


}
