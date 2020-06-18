package org.agilemetrics.core.agilemetrics.business.service

import org.agilemetrics.core.agilemetrics.business.domain.CycleTime
import org.agilemetrics.core.agilemetrics.infrastructure.repository.CycleTimeRepository
import org.agilemetrics.core.agilemetrics.infrastructure.repository.document.CycleTimeDocument
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Month
import javax.annotation.PostConstruct

@Service
class CycleTimeScatterPlotService(
        private val cycleTimeRepository: CycleTimeRepository
) {

    fun getValues() = cycleTimeRepository.findAll()

    @PostConstruct
    fun mockData() {
        listOf(
                CycleTime(startDate = LocalDate.of(2020, Month.FEBRUARY, 7),
                        endDate = LocalDate.of(2020, Month.FEBRUARY, 10)),
                CycleTime(startDate = LocalDate.of(2020, Month.FEBRUARY, 11),
                        endDate = LocalDate.of(2020, Month.MARCH, 2)),
                CycleTime(startDate = LocalDate.of(2020, Month.FEBRUARY, 21),
                        endDate = LocalDate.of(2020, Month.FEBRUARY, 26)),
                CycleTime(startDate = LocalDate.of(2020, Month.FEBRUARY, 14),
                        endDate = LocalDate.of(2020, Month.MARCH, 2)),
                CycleTime(startDate = LocalDate.of(2020, Month.MARCH, 16),
                        endDate = LocalDate.of(2020, Month.MARCH, 30)),
                CycleTime(startDate = LocalDate.of(2020, Month.MARCH, 19),
                        endDate = LocalDate.of(2020, Month.MARCH, 31)),
                CycleTime(startDate = LocalDate.of(2020, Month.APRIL, 2),
                        endDate = LocalDate.of(2020, Month.APRIL, 13)),
                CycleTime(startDate = LocalDate.of(2020, Month.APRIL, 1),
                        endDate = LocalDate.of(2020, Month.APRIL, 13)),
                CycleTime(startDate = LocalDate.of(2020, Month.MARCH, 3),
                        endDate = LocalDate.of(2020, Month.MAY, 4)),
                CycleTime(startDate = LocalDate.of(2020, Month.APRIL, 28),
                        endDate = LocalDate.of(2020, Month.MAY, 5)),
                CycleTime(startDate = LocalDate.of(2020, Month.APRIL, 22),
                        endDate = LocalDate.of(2020, Month.MAY, 5)),
                CycleTime(startDate = LocalDate.of(2020, Month.APRIL, 21),
                        endDate = LocalDate.of(2020, Month.MAY, 5)),
                CycleTime(startDate = LocalDate.of(2020, Month.APRIL, 23),
                        endDate = LocalDate.of(2020, Month.MAY, 8)),
                CycleTime(startDate = LocalDate.of(2020, Month.MARCH, 18),
                        endDate = LocalDate.of(2020, Month.MAY, 8)),
                CycleTime(startDate = LocalDate.of(2020, Month.APRIL, 2),
                        endDate = LocalDate.of(2020, Month.MAY, 19)),
                CycleTime(startDate = LocalDate.of(2020, Month.MAY, 13),
                        endDate = LocalDate.of(2020, Month.MAY, 28)),
                CycleTime(startDate = LocalDate.of(2020, Month.MAY, 11),
                        endDate = LocalDate.of(2020, Month.MAY, 28)),
                CycleTime(startDate = LocalDate.of(2020, Month.MAY, 28),
                        endDate = LocalDate.of(2020, Month.JUNE, 1)),
                CycleTime(startDate = LocalDate.of(2020, Month.MAY, 5),
                        endDate = LocalDate.of(2020, Month.JUNE, 8)),
                CycleTime(startDate = LocalDate.of(2020, Month.JUNE, 3),
                        endDate = LocalDate.of(2020, Month.JUNE, 5))

        ).forEach {
            cycleTimeRepository.save(CycleTimeDocument.from(it)).subscribe()
        }
    }

}
