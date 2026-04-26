package br.com.abegg.abeflow.service.datasources.dynamicobject.mappers

import br.com.abegg.abeflow.service.datasources.dynamicobject.model.DynamicObjectKey
import br.com.abegg.abeflow.service.datasources.dynamicobject.model.DynamicObjectModel
import br.com.abegg.abeflow.service.entities.DynamicObject

fun DynamicObjectModel.toEntity(): DynamicObject {
    return DynamicObject(
        id = this.id.id,
        version = this.id.version,
        name = this.name,
        description = this.description,
        type = this.type,
        status = this.status,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
        isMain = this.isMain,
        content = this.content
    )
}

fun DynamicObject.toModel(): DynamicObjectModel {
    return DynamicObjectModel(
        id = DynamicObjectKey(
            id = this.id,
            version = this.version
        ),
        name = this.name,
        description = this.description,
        type = this.type,
        status = this.status,
        createdBy = this.createdBy,
        createdAt = this.createdAt,
        content = this.content,
        isMain = this.isMain
    )
}