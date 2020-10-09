package org.agilemetrics.core.agilemetrics.infrastructure.azure.exception

import java.lang.RuntimeException

class AzureException : RuntimeException {
    constructor(message: String, ex: Throwable) : super(message, ex) {}
    constructor(message: String) : super(message) {}
}