package org.agilemetrics.core.agilemetrics.presentation.controller.percentile

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
import org.springframework.http.HttpStatus
import java.io.File

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PercentileControllerTests(@Autowired val client: TestRestTemplate,
                                @Autowired val workItemRepository: WorkItemRepository) {

    companion object {
        const val PERCENTILE_ENDPOINT = "/api/v1/percentiles"
        const val WORK_ITEM_ENDPOINT = "/api/v1/work-items"
    }

    @BeforeEach
    fun beforeEach() {
        this.workItemRepository
                .deleteAll()
                .subscribe()
    }

    @Test
    fun when_getPercentiles_then_returnTheMostRelevantPercentiles() {
        // Given:
        populateDatabase()

        // When:
        val result = client.getForEntity(PERCENTILE_ENDPOINT, PercentileOut::class.java)

        // Then:
        assertThat(result).isNotNull
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body).isNotNull
        assertThat(result.body?.percentile50InDays).isEqualTo(13)
        assertThat(result.body?.percentile70InDays).isEqualTo(18)
        assertThat(result.body?.percentile85InDays).isEqualTo(41)
        assertThat(result.body?.percentile95InDays).isEqualTo(52)
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

}
