package br.com.abegg.abeflow.service.transportlayers.impl

import br.com.abegg.abeflow.service.entities.DynamicObject
import br.com.abegg.abeflow.service.iteractors.DynamicObjectService
import br.com.abegg.abeflow.service.transportlayers.DynamicObjectApi
import org.springframework.web.bind.annotation.RestController

@RestController
class DynamicObjectApiImpl(
    val service: DynamicObjectService
) : DynamicObjectApi {

    override fun query(authenticatedUser: String) = service.query(authenticatedUser)

    override fun get(id: String, version: Integer, authenticatedUser: String) = 
        service.get(id, version, authenticatedUser)

    override fun save(data: DynamicObject) = service.save(data)
}