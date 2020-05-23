package org.agilemetrics.core.agilemetrics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class AgileMetricsApplication

fun main(args: Array<String>) {
	runApplication<AgileMetricsApplication>(*args)
}
