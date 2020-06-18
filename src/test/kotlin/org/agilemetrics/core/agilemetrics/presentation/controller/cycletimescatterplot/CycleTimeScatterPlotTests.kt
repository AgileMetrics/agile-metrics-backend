package org.agilemetrics.core.agilemetrics.presentation.controller.cycletimescatterplot

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CycleTimeScatterPlotTests(@Autowired val client: TestRestTemplate) {

    companion object {
        const val CYCLE_TIME_SCATTER_PLOT_ENDPOINT = "/api/v1/cycle-time-scatterplot"
        const val NUMBER_OF_CYCLETIMES_EXPECTED = 20
    }

    @Test
    fun when_getCycleTimesScatterPlot_then_returnCycleTimeScatterPlotList() {
        // Given:

        // When:
        val result = client.getForObject(CYCLE_TIME_SCATTER_PLOT_ENDPOINT,
                List::class.java)

        // Then:
        assertThat(result).isNotNull
        assertThat(result.size).isEqualTo(NUMBER_OF_CYCLETIMES_EXPECTED)
    }

}