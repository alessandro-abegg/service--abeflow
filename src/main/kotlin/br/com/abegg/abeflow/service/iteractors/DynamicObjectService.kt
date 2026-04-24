package br.com.abegg.abeflow.service.iteractors

import br.com.abegg.abeflow.service.entities.DynamicObject
import br.com.abegg.abeflow.service.iteractors.components.DynamicObjectValidatorComponent
import br.com.abegg.abeflow.service.repositories.DynamicObjectRepository
import org.springframework.stereotype.Service

@Service
class DynamicObjectService(
    private val repository: DynamicObjectRepository,
    private val validator: DynamicObjectValidatorComponent
) {

    fun query() = repository.query()

    fun get(id: String, version: Integer) = repository.get(id, version)

    fun save(data: DynamicObject): DynamicObject {
        validator.validate(data)

        return repository.save(data)
    }
}