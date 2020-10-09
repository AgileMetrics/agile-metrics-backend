package org.agilemetrics.core.agilemetrics.business.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
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

        val resource: Resource = ClassPathResource("data/work-items.json")

        val workItems: List<WorkItem> = mapper.readValue(
                resource.inputStream,
                Array<WorkItem>::class.java)
                .toList()

        this.workItemService
                .drop()

        this.workItemService
                .bulk(Flux.fromIterable(workItems))
                .subscribe()
    }

}