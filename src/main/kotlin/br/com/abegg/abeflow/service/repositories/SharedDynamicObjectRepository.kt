package br.com.abegg.abeflow.service.repositories

import br.com.abegg.abeflow.service.entities.SharedDynamicObject

interface SharedDynamicObjectRepository {
    fun findByDynamicObject(dynamicObjectId: String, version: Integer): SharedDynamicObject?

    fun save(data: SharedDynamicObject): SharedDynamicObject

    fun delete(dynamicObjectId: String, version: Integer): Boolean
}
