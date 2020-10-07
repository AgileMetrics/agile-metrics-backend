package org.agilemetrics.core.agilemetrics.infrastructure.azure

import org.agilemetrics.core.agilemetrics.AgileMetricsApplication
import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.test.StepVerifier
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [AgileMetricsApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
internal class AzureAdapterTest {
    @Autowired
    private lateinit var azureAdapter: AzureAdapter

    @Test
    fun shouldRetrieveWorkItemsFromAzure() {
        StepVerifier
                .create(azureAdapter.retrieveAzureWorkItems())
                .thenConsumeWhile { items -> assertWorkItems(items) }
                .expectComplete()
                .verify()
    }

//    @Test
//    fun shouldThrowExceptionIfFailRetrievingFromAzure() {
//        StepVerifier
//                .create(azureAdapter.retrieveAzureWorkItems())
//                .thenConsumeWhile { items -> assertWorkItems(items) }
//                .expectComplete()
//                .verify()
//    }

    private fun assertWorkItems(items: List<WorkItem>): Boolean {
        assertEquals(2, items.size)

        assertNull(items[0].id)
        assertEquals("PoC Montecarlo", items[0].name)
        assertEquals(parseDate("2020-06-08T06:41:28.610Z"), items[0].created)
        assertEquals(2, items[0].transitions.size)
        assertEquals(parseDate("2020-06-08T06:41:31.087Z"), items[0].transitions[0].date)
        assertEquals("To Do", items[0].transitions[0].column)
        assertEquals(parseDate("2020-06-08T06:41:45.703Z"), items[0].transitions[1].date)
        assertEquals("Doing", items[0].transitions[1].column)

        assertNull(items[1].id)
        assertEquals("Persistencia en MongoDB", items[1].name)
        assertEquals(parseDate("2020-06-08T06:41:42.823Z"), items[1].created)
        assertEquals(3, items[1].transitions.size)
        assertEquals(parseDate("2020-06-08T06:41:45.400Z"), items[1].transitions[0].date)
        assertEquals("To Do", items[1].transitions[0].column)
        assertEquals(parseDate("2020-06-08T06:41:45.703Z"), items[1].transitions[1].date)
        assertEquals("Doing", items[1].transitions[1].column)
        assertEquals(parseDate("9999-01-01T00:00Z"), items[1].transitions[2].date)
        assertEquals("Done", items[1].transitions[2].column)
        return true

    }

    private fun parseDate(dateAsString: String): LocalDateTime {
        return LocalDateTime.parse(dateAsString, DateTimeFormatter.ISO_DATE_TIME)
    }
}
