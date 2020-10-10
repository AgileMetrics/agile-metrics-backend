package org.agilemetrics.core.agilemetrics.infrastructure.azure.model

open class AzureApiContext {
    private var _organization: String? = null
    val organization: String? get() = _organization

    private var _project: String? = null
    val project: String? get() = _project

    private var _username: String? = null
    val username: String? get() = _username

    private var _password: String? = null
    val password: String? get() = _password

    fun setContext(organization: String, project: String, username: String, password: String) {
        this._organization = organization
        this._project = project
        this._username = username
        this._password = password
    }
}