package br.com.abegg.abeflow.service.datasources.dynamicobject.mappers

import br.com.abegg.abeflow.service.datasources.dynamicobject.model.SharedDynamicObjectModel
import br.com.abegg.abeflow.service.entities.SharedDynamicObject

fun SharedDynamicObjectModel.toEntity(): SharedDynamicObject {
    return SharedDynamicObject(
        id = this.id,
        dynamicObjectId = this.dynamicObjectId,
        dynamicObjectVersion = this.dynamicObjectVersion,
        sharedBy = this.sharedBy,
        sharedWith = this.sharedWith
    )
}

fun SharedDynamicObject.toModel(): SharedDynamicObjectModel {
    return SharedDynamicObjectModel(
        id = this.id ?: "",
        dynamicObjectId = this.dynamicObjectId,
        dynamicObjectVersion = this.dynamicObjectVersion,
        sharedBy = this.sharedBy,
        sharedWith = this.sharedWith
    )
}
