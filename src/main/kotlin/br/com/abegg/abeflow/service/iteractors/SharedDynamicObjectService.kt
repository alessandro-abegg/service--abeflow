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

        val existing = repository.findByDynamicObject(data.dynamicObjectId, data.dynamicObjectVersion)
        val dataToSave = if (existing != null) {
            // Allow if the user is the owner or already shared with
            if (existing.sharedBy != data.sharedBy && data.sharedBy !in existing.sharedWith) {
                throw IllegalAccessException("Only the owner or shared users can manage shares")
            }
            // Keep the original owner
            data.copy(id = existing.id, sharedBy = existing.sharedBy)
        } else {
            data
        }

        return repository.save(dataToSave)
    }

    fun unshare(dynamicObjectId: String, version: Integer, authenticatedUser: String): Boolean {
        val existing = repository.findByDynamicObject(dynamicObjectId, version) ?: return false

        return if (existing.sharedBy == authenticatedUser) {
            // Owner: delete the entire share
            repository.delete(dynamicObjectId, version)
        } else if (authenticatedUser in existing.sharedWith) {
            // Shared user: remove themselves from the list
            val updatedSharedWith = existing.sharedWith - authenticatedUser
            val updated = existing.copy(sharedWith = updatedSharedWith)
            repository.save(updated)
            true
        } else {
            false
        }
    }

    fun getShares(dynamicObjectId: String, version: Integer, authenticatedUser: String): SharedDynamicObject? {
        val shares = repository.findByDynamicObject(dynamicObjectId, version)
        return shares?.takeIf { it.sharedBy == authenticatedUser || authenticatedUser in it.sharedWith }
    }
}
