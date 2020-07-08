package org.agilemetrics.core.agilemetrics.business.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.io.File
import javax.annotation.PostConstruct

@Component
@ConditionalOnProperty(
        value = ["populate-database"],
        havingValue = "true",
        matchIfMissing = false)
class PopulateDatabase(
        private val workItemService: WorkItemService
) {

    @PostConstruct
    fun populateDatabase() {
        val mapper: ObjectMapper = ObjectMapper()
                .registerModule(KotlinModule())
                .registerModule(JavaTimeModule())

        val workItems: List<WorkItem> = mapper.readValue(
                File(javaClass.classLoader.getResource("work-items.json").file),
                Array<WorkItem>::class.java)
                .toList()

        this.workItemService
                .bulk(Flux.fromIterable(workItems))
    }

}