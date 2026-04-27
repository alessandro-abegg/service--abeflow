package br.com.abegg.abeflow.service.repositories

import br.com.abegg.abeflow.service.entities.DynamicObject

interface DynamicObjectRepository {
    fun query(authenticatedUser: String): List<DynamicObject>

    fun get(id: String, version: Int, authenticatedUser: String): DynamicObject?

    fun save(data: DynamicObject): DynamicObject
}