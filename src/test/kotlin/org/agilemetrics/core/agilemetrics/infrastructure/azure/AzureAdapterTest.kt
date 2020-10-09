package org.agilemetrics.core.agilemetrics.infrastructure.azure

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.azure.mapper.WorkItemMapper
import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureWorkItem
import org.agilemetrics.core.agilemetrics.infrastructure.azure.services.AzureApiService
import org.agilemetrics.core.agilemetrics.infrastructure.azure.services.AzureWorkItemService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
internal class AzureAdapterTest {
    companion object {
        private const val AZURE_WORK_ITEM_NAME: String = "Calcular percentile (SLAs)"
        private val AZURE_WORK_ITEM_CREATED_DATE: LocalDateTime = LocalDateTime.parse("2020-06-08T06:44:55.387")
        private val AZURE_WORK_ITEM_MODIFICATION_DATE_WIP: LocalDateTime = LocalDateTime.parse("2020-08-06T06:42:12.030")
        private val AZURE_WORK_ITEM_MODIFICATION_DATE_DONE: LocalDateTime = LocalDateTime.parse("2020-08-06T06:49:41.353")
    }

    private lateinit var azureAdapter: AzureAdapter

    @Mock
    private lateinit var azureWorkItemService: AzureWorkItemService

    @Mock
    private lateinit var azureApiService: AzureApiService

    @BeforeEach
    fun setup() {
        azureAdapter = AzureAdapter(azureWorkItemService = azureWorkItemService, azureApiService = azureApiService, workItemMapper = WorkItemMapper())
    }

    @Test
    fun shouldRetrieveWorkItemFromCurrentIteration() {
        val workItemIds = Mono.just(listOf(2L))
        val expectedIterationId = "d42eda64-9c72-4b1b-8b2a-8cfa7cb69e75"
        Mockito.`when`(azureApiService.getCurrentIterationId()).thenReturn(Mono.just(expectedIterationId))
        Mockito.`when`(azureApiService.getWorkItemIdsByIterationId(expectedIterationId)).thenReturn(workItemIds)
        Mockito.`when`(azureWorkItemService.retrieveAzureWorkItems(workItemIds)).thenReturn(Flux.just(createAzureWorkItem()))

        StepVerifier
                .create(azureAdapter.retrieveWorkItemFromCurrentIteration())
                .assertNext { assertWorkItem1(it) }
                .expectComplete()
                .verify()
    }

    @Test
    fun shouldRetrieveAllWorkItemsWithDoneStatus() {
        val workItemIds = Mono.just(listOf(2L))

        Mockito.`when`(azureApiService.executeWorkItemQuery("Select [System.Id] From WorkItems Where [System.State] = 'Done'"))
                .thenReturn(workItemIds)

        Mockito.`when`(azureWorkItemService.retrieveAzureWorkItems(MockitoHelper.anyObject())).thenReturn(Flux.just(createAzureWorkItem()))

        StepVerifier
                .create(azureAdapter.retrieveAllWorkItemsWithDoneStatus())
                .assertNext { assertWorkItem1(it) }
                .expectComplete()
                .verify()
    }

    private fun assertWorkItem1(item: WorkItem) {
        assertNull(item.id)
        assertEquals(AZURE_WORK_ITEM_NAME, item.name)
        assertEquals(AZURE_WORK_ITEM_CREATED_DATE, item.created)
        assertEquals(2, item.transitions.size)
        assertEquals(AZURE_WORK_ITEM_MODIFICATION_DATE_WIP, item.transitions[0].date)
        assertEquals("WIP", item.transitions[0].column)
        assertEquals(AZURE_WORK_ITEM_MODIFICATION_DATE_DONE, item.transitions[1].date)
        assertEquals("DONE", item.transitions[1].column)
    }

    private fun createAzureWorkItem(): AzureWorkItem {

        return AzureWorkItem(id = null, name = AZURE_WORK_ITEM_NAME, created = AZURE_WORK_ITEM_CREATED_DATE, transitions = listOf(
                AzureWorkItem.WorkItemTransition(column = "To Do", date = AZURE_WORK_ITEM_CREATED_DATE),
                AzureWorkItem.WorkItemTransition(column = "Doing", date = AZURE_WORK_ITEM_MODIFICATION_DATE_WIP),
                AzureWorkItem.WorkItemTransition(column = "Done", date = AZURE_WORK_ITEM_MODIFICATION_DATE_DONE)
        ))
    }

    //Hack because I don't how to mock a mono correctly
    object MockitoHelper {
        fun <T> anyObject(): T {
            Mockito.any<T>()
            return uninitialized()
        }
        @Suppress("UNCHECKED_CAST")
        fun <T> uninitialized(): T =  null as T
    }


}
