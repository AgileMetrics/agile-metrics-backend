package org.agilemetrics.core.agilemetrics.infrastructure.repository

import org.agilemetrics.core.agilemetrics.infrastructure.repository.document.CycleTimeDocument
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface CycleTimeRepository : ReactiveMongoRepository<CycleTimeDocument, String>