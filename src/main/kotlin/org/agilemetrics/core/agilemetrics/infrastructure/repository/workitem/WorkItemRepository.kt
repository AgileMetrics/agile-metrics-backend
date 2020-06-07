package org.agilemetrics.core.agilemetrics.infrastructure.repository.workitem

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkItemRepository : ReactiveMongoRepository<WorkItemDocument, String>
