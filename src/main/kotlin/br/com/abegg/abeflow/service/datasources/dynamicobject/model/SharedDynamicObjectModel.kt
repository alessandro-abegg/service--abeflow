package br.com.abegg.abeflow.service.datasources.dynamicobject.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class Share(
    val sharedBy: String,
    val sharedWith: String,
    val canReshare: Boolean = false
)

@Document(collection = "shared_dynamic_objects")
data class SharedDynamicObjectModel(
    @Id val id: String,
    val dynamicObjectId: String,
    val dynamicObjectVersion: Integer,
    val sharedBy: String,
    val shares: List<Share> = emptyList()
)
