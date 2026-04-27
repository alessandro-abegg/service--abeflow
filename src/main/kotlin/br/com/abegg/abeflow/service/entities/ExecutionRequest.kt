package br.com.abegg.abeflow.service.entities

data class ExecutionRequest(
    val dynamicObjectId: String,
    val version: Int,
    val formData: Map<String, Any>,
    val authenticatedUser: String
)