package org.agilemetrics.core.agilemetrics.infrastructure.azure.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AzureConfiguration() {

    @Bean
    fun azureWebClient(@Value("\${agile-metrics.azure-project.base-url}") baseUrl: String,
                       @Value("\${agile-metrics.azure-project.username}") username: String,
                       @Value("\${agile-metrics.azure-project.password}") password: String
                       ): WebClient {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders { headers -> headers.setBasicAuth(username, password) }
                .build()
    }
}