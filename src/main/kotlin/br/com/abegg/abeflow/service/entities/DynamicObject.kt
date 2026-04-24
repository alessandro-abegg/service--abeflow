package br.com.abegg.abeflow.service.entities

data class DynamicObject(
    val id: String,
    val version: Integer,
    val name: String,
    val description: String,
    val type: DynamicObjectType,
    val status: DynamicObjectStatus,
    val author: String,
    val createdAt: String,
    val content: Map<String, Any>
)