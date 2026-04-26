package br.com.abegg.abeflow.service.entities

data class DynamicObject(
    val id: String,
    val version: Integer,
    val name: String,
    val description: String,
    val type: DynamicObjectType,
    val status: DynamicObjectStatus,
    val createdBy: String,
    val createdAt: String,
    val isMain: Boolean,
    val isPublished: Boolean = false,
    val content: Map<String, Any>
)