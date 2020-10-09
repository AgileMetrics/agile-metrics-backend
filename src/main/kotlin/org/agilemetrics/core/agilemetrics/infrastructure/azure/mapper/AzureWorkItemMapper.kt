package org.agilemetrics.core.agilemetrics.infrastructure.azure.mapper

import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch.AzureWorkItemBatchResponseDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.batch.AzureWorkItemBatchResponseDto.AzureWorkItemInformation
import org.agilemetrics.core.agilemetrics.infrastructure.azure.dto.update.AzureWorkItemUpdateInformationDto
import org.agilemetrics.core.agilemetrics.infrastructure.azure.exception.AzureException
import org.agilemetrics.core.agilemetrics.infrastructure.azure.model.AzureWorkItem
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class AzureWorkItemMapper {

    fun mapToAzureWorkItem(azureWorkItemBatchResponseDto:AzureWorkItemBatchResponseDto, azureWorkItemUpdateInformationDto: AzureWorkItemUpdateInformationDto): AzureWorkItem {
        val azureAzureWorkItemInformationBatchResponseMap: HashMap<Long, AzureWorkItemInformation> = azureWorkItemBatchResponseDto.getAzureWorkItemInformationAsMap()
        val workItemId = azureWorkItemUpdateInformationDto.getWorkItemId()
        if (azureAzureWorkItemInformationBatchResponseMap[workItemId] == null) {
            throw AzureException("data error, workItemUpdateInformation not belong to azureWorkItem") //This never should happen
        }
        return createAzureWorkItem(azureAzureWorkItemInformationBatchResponseMap[workItemId]!!, azureWorkItemUpdateInformationDto)
    }

    private fun createAzureWorkItem(azureWorkItemInformation: AzureWorkItemInformation, azureWorkItemUpdateInformationDto: AzureWorkItemUpdateInformationDto): AzureWorkItem {
        return AzureWorkItem(id = null,
                name = azureWorkItemInformation.fields.title,
                created = azureWorkItemInformation.fields.createdDate,
                transitions = createTransition(azureWorkItemUpdateInformationDto))
    }

    private fun createTransition(azureWorkItemUpdateInformationDto: AzureWorkItemUpdateInformationDto?): List<AzureWorkItem.WorkItemTransition> {
        return azureWorkItemUpdateInformationDto!!.value //review !!
                .filter { item -> item.fields.boardColumn?.newValue != null }
                .map {
                    val transitionDate: LocalDateTime = getDateFromTransition(it.fields)
                    AzureWorkItem.WorkItemTransition(column = it.fields.boardColumn!!.newValue!!, date = transitionDate)
                }
    }

    private fun getDateFromTransition(transitionFields: AzureWorkItemUpdateInformationDto.Fields): LocalDateTime {
        return when {
            transitionFields.createdDate?.newValue != null -> LocalDateTime.parse(transitionFields.createdDate.newValue, DateTimeFormatter.ISO_DATE_TIME)
            transitionFields.changedDate?.newValue != null -> LocalDateTime.parse(transitionFields.changedDate.newValue, DateTimeFormatter.ISO_DATE_TIME)
            else -> throw AzureException("data error, workItemUpdateInformation not has a date") //This never should happen
        }
    }




}