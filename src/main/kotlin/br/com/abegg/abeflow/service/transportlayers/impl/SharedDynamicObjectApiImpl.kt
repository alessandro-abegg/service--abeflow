package br.com.abegg.abeflow.service.transportlayers.impl

import br.com.abegg.abeflow.service.entities.SharedDynamicObject
import br.com.abegg.abeflow.service.iteractors.SharedDynamicObjectService
import br.com.abegg.abeflow.service.transportlayers.SharedDynamicObjectApi
import org.springframework.web.bind.annotation.RestController

@RestController
class SharedDynamicObjectApiImpl(
    val service: SharedDynamicObjectService
) : SharedDynamicObjectApi {

    override fun share(
        id: String,
        version: Integer,
        sharedWith: List<String>,
        authenticatedUser: String
    ): SharedDynamicObject {
        val data = SharedDynamicObject(
            dynamicObjectId = id,
            dynamicObjectVersion = version,
            sharedBy = authenticatedUser,
            sharedWith = sharedWith
        )
        return service.share(data)
    }

    override fun unshare(
        id: String,
        version: Integer,
        authenticatedUser: String
    ): Boolean {
        return service.unshare(id, version, authenticatedUser)
    }

    override fun getShares(
        id: String,
        version: Integer,
        authenticatedUser: String
    ): SharedDynamicObject? {
        return service.getShares(id, version, authenticatedUser)
    }
}
