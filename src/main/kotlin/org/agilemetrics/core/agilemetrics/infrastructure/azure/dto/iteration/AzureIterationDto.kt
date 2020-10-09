package org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.iteration

data class AzureIterationDto(val count: Int, val value: List<Iteration>) {
    data class Iteration(val id: String,
                         val name: String)
}
