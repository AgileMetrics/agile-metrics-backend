package org.agilemetrics.core.agilemetrics.infrastructure.azure.mapper

import org.agilemetrics.core.agilemetrics.business.domain.WorkItem
import org.agilemetrics.core.agilemetrics.business.domain.WorkItemTransition
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.AzureWorkItemUpdateInformationDto
import org.springframework.stereotype.Service

@Service
class AzureWorkItemMapper {

    fun mapToDomain(azureAzureWorkItem: AzureWorkItemDto.AzureWorkItem, azureWorkItemUpdateInformationDto: AzureWorkItemUpdateInformationDto): WorkItem{
        return WorkItem(id = null,
                name = azureAzureWorkItem.fields.title,
                created = azureAzureWorkItem.fields.createdDate,
                transitions = mapTransition(azureWorkItemUpdateInformationDto))
    }

    private fun mapTransition(azureWorkItemUpdateInformationDto: AzureWorkItemUpdateInformationDto?): List<WorkItemTransition> {
        return azureWorkItemUpdateInformationDto!!.value //review !!
                .filter { item -> item.fields.boardColumn?.newValue != null }
                .map { item -> WorkItemTransition(column = item.fields.boardColumn!!.newValue!!, date = item.revisedDate) }
    }


}