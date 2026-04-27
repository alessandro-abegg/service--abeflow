package br.com.abegg.abeflow.service.iteractors

import br.com.abegg.abeflow.service.repositories.DynamicObjectExecutionRepository
import org.springframework.stereotype.Service

@Service
class DynamicObjectExecutionUseCase(
    val repository: DynamicObjectExecutionRepository
) {

    fun execute(dynamicObjectId: String, version: Int, formData: Map<String, Any>) {
        repository.execute(dynamicObjectId, version, formData)
    }
}
