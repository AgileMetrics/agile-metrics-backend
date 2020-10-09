package org.agilemetrics.core.agilemetrics.infrastructure.azure.configuration

import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureApiContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AzureConfiguration() {

    @Bean
    fun azureWebClient(@Value("\${agile-metrics.azure-project.base-url}") baseUrl: String): WebClient {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build()
    }

    @Bean
    @RequestScope
    fun createAzureApiContext():AzureApiContext  {
        return AzureApiContext()
    }
}