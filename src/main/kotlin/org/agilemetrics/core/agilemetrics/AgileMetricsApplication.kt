package org.agilemetrics.core.agilemetrics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AgileMetricsApplication

fun main(args: Array<String>) {
	runApplication<AgileMetricsApplication>(*args)
}
