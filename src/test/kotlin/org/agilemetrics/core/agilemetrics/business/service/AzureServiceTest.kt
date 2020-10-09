package org.agilemetrics.core.agilemetrics.business.service

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.azure.AzureAdapter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ExtendWith(MockitoExtension::class)
internal class AzureServiceTest {
    @Mock
    private lateinit var azureAdapter: AzureAdapter

    @Mock
    private lateinit var workItemService: WorkItemService

    private lateinit var azureService: AzureService

    @BeforeEach
    fun setup() {
        azureService = AzureService(azureAdapter = azureAdapter, workItemService = workItemService)
    }

    @Test
    @DisplayName( " Given a azureService object "
            + " When invoke to populateDatabaseFromAzure "
            + " Then retrieve works items from azure "
            + " And save it in the database "
            + " And return a mono to let subscribe to the flow ")
    fun shouldTestPopulateDatabaseFromAzure() {
        // Given
        val workItemFlux = Flux.just(WorkItem(id = null, name = "test", created = null, transitions = listOf()))
        val expectedResult: Mono<Void> = Mono.empty()
        Mockito.`when`(azureAdapter.retrieveAllWorkItemsWithDoneStatus()).thenReturn(workItemFlux)
        Mockito.`when`(workItemService.bulk(workItemFlux)).thenReturn(expectedResult)

        // When
        val result: Mono<Void> = azureService.populateDatabaseFromAzure()

        // Then
        Assertions.assertEquals(expectedResult, result)
    }
}