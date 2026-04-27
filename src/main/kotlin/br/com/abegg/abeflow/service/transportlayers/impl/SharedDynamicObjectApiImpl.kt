package br.com.abegg.abeflow.service.transportlayers.impl

import br.com.abegg.abeflow.lib.SecurityContext.getUserId
import br.com.abegg.abeflow.service.entities.ShareModel
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
        version: Int,
        sharedWith: List<String>
    ): SharedDynamicObject {
        val authenticatedUser = getUserId()

        val data = SharedDynamicObject(
            dynamicObjectId = id,
            dynamicObjectVersion = version,
            sharedBy = authenticatedUser,
            sharedWith = sharedWith.map {
                ShareModel(
                    sharedWith = it,
                    sharedBy = authenticatedUser,
                    canReshare = true
                )
            }
        )
        return service.share(data)
    }

    override fun unshare(
        id: String,
        version: Int
    ): Boolean {
        return service.unshare(id, version)
    }

    override fun getShares(
        id: String,
        version: Int
    ): SharedDynamicObject? {
        return service.getShares(id, version)
    }
}
