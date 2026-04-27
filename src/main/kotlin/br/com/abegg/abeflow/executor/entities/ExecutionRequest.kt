package br.com.abegg.abeflow.executor.entities

data class ExecutionRequest(
    val dynamicObjectId: String,
    val version: Int,
    val formData: Map<String, Any>,
    val authenticatedUser: String
)