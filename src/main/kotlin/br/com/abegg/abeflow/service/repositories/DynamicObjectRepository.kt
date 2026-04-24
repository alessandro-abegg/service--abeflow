package br.com.abegg.abeflow.service.repositories

import br.com.abegg.abeflow.service.entities.DynamicObject

interface DynamicObjectRepository {
    fun query(): List<DynamicObject>
    fun get(id: String, version: Integer): DynamicObject?
    fun save(data: DynamicObject): DynamicObject
}