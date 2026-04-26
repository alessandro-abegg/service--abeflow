package br.com.abegg.abeflow.service.datasources.dynamicobject.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "shared_dynamic_objects")
data class SharedDynamicObjectModel(
    @Id val id: String,
    val dynamicObjectId: String,
    val dynamicObjectVersion: Integer,
    val sharedBy: String,
    val sharedWith: List<String> = emptyList()
)
