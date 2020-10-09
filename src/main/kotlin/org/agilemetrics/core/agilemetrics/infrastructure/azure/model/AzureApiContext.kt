package org.agilemetrics.core.agilemetrics.infrastructure.azure.model

open class AzureApiContext {
    private var organization: String? = null
    private var project: String? = null
    private var username: String? = null
    private var password: String? = null

    fun getOrganization(): String? {
        return this.organization
    }

    fun getProject(): String? {
        return this.project
    }

    fun getUsername(): String? {
        return this.username
    }

    fun getPassword(): String? {
        return this.password
    }

    fun setContext(organization: String?, project: String?, username: String?, password: String?) {
        this.organization = organization
        this.project = project
        this.username = username
        this.password = password
    }
}