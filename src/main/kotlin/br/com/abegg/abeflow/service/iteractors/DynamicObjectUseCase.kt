package br.com.abegg.abeflow.service.iteractors

import br.com.abegg.abeflow.lib.SecurityContext.getUserId
import br.com.abegg.abeflow.service.entities.DynamicObject
import br.com.abegg.abeflow.service.iteractors.components.DynamicObjectValidatorComponent
import br.com.abegg.abeflow.service.repositories.DynamicObjectRepository
import org.springframework.stereotype.Service

@Service
class DynamicObjectUseCase(
    private val repository: DynamicObjectRepository,
    private val validator: DynamicObjectValidatorComponent
) {

    fun query() = repository.query(getUserId())

    fun get(id: String, version: Int) =
        repository.get(id, version, getUserId())

    fun save(data: DynamicObject): DynamicObject {
        validator.validate(data)

        return repository.save(data)
    }
}