package br.com.abegg.abeflow.service.iteractors

import br.com.abegg.abeflow.service.entities.SharedDynamicObject
import br.com.abegg.abeflow.service.repositories.SharedDynamicObjectRepository
import org.springframework.stereotype.Service

@Service
class SharedDynamicObjectService(
    private val repository: SharedDynamicObjectRepository
) {

    fun share(data: SharedDynamicObject): SharedDynamicObject {
        // Basic validation
        require(data.dynamicObjectId.isNotBlank()) { "DynamicObjectId cannot be blank" }
        require(data.sharedBy.isNotBlank()) { "SharedBy cannot be blank" }

        return repository.save(data)
    }

    fun unshare(dynamicObjectId: String, version: Integer, authenticatedUser: String): Boolean {
        return repository.unshare(dynamicObjectId, version, authenticatedUser)
    }

    fun getShares(dynamicObjectId: String, version: Integer, authenticatedUser: String): SharedDynamicObject? {
        val shares = repository.findByDynamicObject(dynamicObjectId, version)
        return shares?.takeIf { it.sharedBy == authenticatedUser || authenticatedUser in it.sharedWith }
    }
}
