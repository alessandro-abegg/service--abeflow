package br.com.abegg.abeflow.service.entities

data class ShareModel(
    val sharedBy: String,
    val sharedWith: String,
    val canReshare: Boolean = false
)

data class SharedDynamicObject(
    val id: String? = null,
    val dynamicObjectId: String,
    val dynamicObjectVersion: Int,
    val sharedBy: String,
    val sharedWith: List<ShareModel> = emptyList()
)
