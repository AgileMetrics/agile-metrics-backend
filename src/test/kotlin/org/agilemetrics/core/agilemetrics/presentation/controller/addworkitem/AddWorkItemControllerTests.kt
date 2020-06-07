package org.agilemetrics.core.agilemetrics.presentation.controller.addworkitem

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddWorkItemControllerTests(@Autowired val client: TestRestTemplate) {

    companion object {
        const val WORK_ITEM_ENDPOINT = "/api/v1/work-item"
        const val WIP_LITERAL = "WIP"
        const val DONE_LITERAL = "Done"
        const val WORK_ITEM_NAME = "Work Item A"
    }

    @Test
    fun when_addWorkItemWithTransitions_then_returnWorkItemWithSameTransitions() {
        // Given:
        val request = getAddWorkItemIn()

        // When:
        val result = client.postForObject(WORK_ITEM_ENDPOINT,
                request,
                AddWorkItemOut::class.java)

        // Then:
        assertThat(result).isNotNull
        assertThat(result.transitions.size).isEqualTo(request.transitions.size)
        assertThat(result.transitions == request.transitions).isTrue()
    }

    @Test
    fun when_addWorkItem_then_returnWorkItemWithIdAndCreatedDate() {
        // Given:
        val request = getAddWorkItemIn()

        // When:
        val result = client.postForObject(WORK_ITEM_ENDPOINT,
                request,
                AddWorkItemOut::class.java)

        // Then:
        assertThat(result).isNotNull
        assertThat(result.id).isNotBlank()
        assertThat(result.created).isNotNull()
    }

    private fun getAddWorkItemIn(): AddWorkItemIn {
        val transitions = listOf(
                AddWorkItemTransition(column = WIP_LITERAL,
                        date = LocalDateTime.now()),
                AddWorkItemTransition(column = DONE_LITERAL,
                        date = LocalDateTime.now())
        )

        return AddWorkItemIn(name = WORK_ITEM_NAME, transitions = transitions)
    }


}
