package br.com.abegg.abeflow.service.transportlayers.http.impl

import br.com.abegg.abeflow.service.iteractors.DynamicObjectExecutionUseCase
import br.com.abegg.abeflow.service.transportlayers.http.DynamicObjectExecutionApi
import org.springframework.web.bind.annotation.RestController

@RestController
class DynamicObjectExecutionApiImpl(
    private val service: DynamicObjectExecutionUseCase
) : DynamicObjectExecutionApi {

    override fun execute(id: String, version: Int, formData: Map<String, Any>) {
        service.execute(id, version, formData)
    }
}
