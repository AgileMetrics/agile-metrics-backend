package org.agilemetrics.core.agilemetrics.infrastructure.repository

import org.agilemetrics.core.agilemetrics.infrastructure.repository.document.CycleTimeDocument
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CycleTimeRepository : ReactiveMongoRepository<CycleTimeDocument, String>
