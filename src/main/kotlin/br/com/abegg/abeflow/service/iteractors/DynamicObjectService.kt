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

    fun query(authenticatedUser: String) = repository.query(authenticatedUser)

    fun get(id: String, version: Integer, authenticatedUser: String) = repository.get(id, version, authenticatedUser)

    fun save(data: DynamicObject): DynamicObject {
        validator.validate(data)

        return repository.save(data)
    }
}