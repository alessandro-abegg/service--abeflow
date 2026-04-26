package br.com.abegg.abeflow.service.entities

data class SharedDynamicObject(
    val id: String? = null,
    val dynamicObjectId: String,
    val dynamicObjectVersion: Integer,
    val sharedBy: String,
    val sharedWith: List<String> = emptyList()
)
