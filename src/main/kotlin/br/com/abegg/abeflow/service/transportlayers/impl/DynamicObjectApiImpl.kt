package br.com.abegg.abeflow.service.transportlayers.impl

import br.com.abegg.abeflow.service.entities.DynamicObject
import br.com.abegg.abeflow.service.iteractors.DynamicObjectService
import br.com.abegg.abeflow.service.transportlayers.DynamicObjectApi
import org.springframework.web.bind.annotation.RestController

@RestController
class DynamicObjectApiImpl(
    val service: DynamicObjectService
) : DynamicObjectApi {

    override fun query() = service.query()

    override fun get(id: String, version: Int) = service.get(id, version)

    override fun save(data: DynamicObject) = service.save(data)
}