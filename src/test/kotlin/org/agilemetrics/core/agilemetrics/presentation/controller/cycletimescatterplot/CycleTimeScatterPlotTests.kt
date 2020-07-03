package org.agilemetrics.core.agilemetrics.presentation.controller.cycletimescatterplot

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.agilemetrics.core.agilemetrics.infrastructure.repository.workitem.WorkItemRepository
import org.agilemetrics.core.agilemetrics.presentation.controller.addworkitem.AddWorkItemIn
import org.agilemetrics.core.agilemetrics.presentation.controller.addworkitem.AddWorkItemOut
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.io.File


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CycleTimeScatterPlotTests(@Autowired val client: TestRestTemplate,
                                @Autowired val workItemRepository: WorkItemRepository) {

    companion object {
        const val CYCLE_TIME_SCATTER_PLOT_ENDPOINT = "/api/v1/cycle-time-scatterplot"
        const val WORK_ITEM_ENDPOINT = "/api/v1/work-item"
        const val NUMBER_OF_CYCLETIMES_EXPECTED = 4
    }

    @BeforeEach
    fun beforeEach() {
        this.workItemRepository
                .deleteAll()
                .subscribe()
    }

    @Test
    fun when_getCycleTimesScatterPlot_then_returnCompleteCycleTimeScatterPlotList() {
        // Given:
        populateDatabase()

        // When:
        val result = client.exchange(CYCLE_TIME_SCATTER_PLOT_ENDPOINT,
                HttpMethod.GET,
                null,
                typeRef<List<CycleTimeScatterPlotOut>>())

        // Then:
        assertThat(result).isNotNull
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.size).isEqualTo(NUMBER_OF_CYCLETIMES_EXPECTED)
    }

    @Test
    fun when_getCycleTimesScatterPlot_then_returnCorrectCycleTimeInDays() {
        // Given:
        populateDatabase()

        // When:
        val result = client.exchange(CYCLE_TIME_SCATTER_PLOT_ENDPOINT,
                HttpMethod.GET,
                null,
                typeRef<List<CycleTimeScatterPlotOut>>())

        // Then:
        val firstCycleTimeResult: CycleTimeScatterPlotOut? = result.body?.get(0)
        assertThat(firstCycleTimeResult?.cycleTimeInDays).isEqualTo(4)
    }

    @Test
    fun when_getCycleTimesScatterPlot_then_returnEmptyList() {
        // Given:
        // Empty Database

        // When:
        val result = client.exchange(CYCLE_TIME_SCATTER_PLOT_ENDPOINT,
                HttpMethod.GET,
                null,
                typeRef<List<CycleTimeScatterPlotOut>>())

        // Then:
        assertThat(result).isNotNull
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.size).isEqualTo(0)
    }

    private fun populateDatabase() {
        val mapper: ObjectMapper = ObjectMapper()
                .registerModule(KotlinModule())
                .registerModule(JavaTimeModule())

        val workItems: List<AddWorkItemIn> = mapper.readValue(File(javaClass.classLoader.getResource("work-items.json").file),
                Array<AddWorkItemIn>::class.java).toList()

        workItems.forEach {
            client.postForObject(WORK_ITEM_ENDPOINT,
                    it,
                    AddWorkItemOut::class.java)
        }
    }

    private inline fun <reified T> typeRef() = object : ParameterizedTypeReference<T>() {}

}