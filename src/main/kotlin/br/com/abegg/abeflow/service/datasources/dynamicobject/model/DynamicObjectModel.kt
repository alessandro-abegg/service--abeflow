package br.com.abegg.abeflow.service.datasources.dynamicobject.model

import br.com.abegg.abeflow.service.entities.DynamicObjectStatus
import br.com.abegg.abeflow.service.entities.DynamicObjectType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "dynamic_objects")
@CompoundIndex(
    name = "idx_performance_lookup",
    def = "{'id.scriptId': 1, 'isMain': -1, 'id.version': -1}"
)
data class DynamicObjectModel(
    @Id val id: DynamicObjectKey,
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

data class DynamicObjectKey(
    val id: String,
    val version: Integer
)