package br.com.abegg.abeflow.service.transportlayers.impl

import br.com.abegg.abeflow.service.iteractors.SharedDynamicObjectUseCase
import br.com.abegg.abeflow.service.transportlayers.DynamicObjectExecutionApi
import org.springframework.web.bind.annotation.RestController

@RestController
class DynamicObjectExecutionApiImpl(
    private val service: SharedDynamicObjectUseCase
) : DynamicObjectExecutionApi {

    override fun execute(id: String, version: Int, formData: Map<String, Any>) {
        service.execute(id, version, formData)
    }
}
