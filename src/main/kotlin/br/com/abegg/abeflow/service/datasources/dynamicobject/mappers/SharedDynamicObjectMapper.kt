package br.com.abegg.abeflow.service.datasources.dynamicobject.mappers

import br.com.abegg.abeflow.service.datasources.dynamicobject.model.SharedDynamicObjectModel
import br.com.abegg.abeflow.service.entities.SharedDynamicObject
import br.com.abegg.abeflow.service.datasources.dynamicobject.model.ShareModel as ShareModelDB
import br.com.abegg.abeflow.service.entities.ShareModel as ShareModelEntity

fun SharedDynamicObjectModel.toEntity(): SharedDynamicObject {
    return SharedDynamicObject(
        id = this.id,
        dynamicObjectId = this.dynamicObjectId,
        dynamicObjectVersion = this.dynamicObjectVersion,
        sharedBy = this.sharedBy,
        sharedWith = this.shares.map { it.toEntity() }
    )
}

fun SharedDynamicObject.toModel(): SharedDynamicObjectModel {
    return SharedDynamicObjectModel(
        id = this.id ?: "",
        dynamicObjectId = this.dynamicObjectId,
        dynamicObjectVersion = this.dynamicObjectVersion,
        sharedBy = this.sharedBy,
        shares = this.sharedWith.map { it.toModel() }
    )
}

fun ShareModelDB.toEntity(): ShareModelEntity {
    return ShareModelEntity(
        sharedBy = this.sharedBy,
        sharedWith = this.sharedWith,
        canReshare = this.canReshare
    )
}

fun ShareModelEntity.toModel(): ShareModelDB {
    return ShareModelDB(
        sharedBy = this.sharedBy,
        sharedWith = this.sharedWith,
        canReshare = this.canReshare
    )
}
