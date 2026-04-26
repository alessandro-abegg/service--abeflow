package br.com.abegg.abeflow.service.iteractors

import br.com.abegg.abeflow.service.entities.SharedDynamicObject
import br.com.abegg.abeflow.service.repositories.SharedDynamicObjectRepository
import org.springframework.stereotype.Service

@Service
class SharedDynamicObjectService(
    private val repository: SharedDynamicObjectRepository
) {

    fun share(data: SharedDynamicObject): SharedDynamicObject {
        require(data.dynamicObjectId.isNotBlank()) { "DynamicObjectId cannot be blank" }
        require(data.sharedBy.isNotBlank()) { "SharedBy (originator) cannot be blank" }

        val existing = repository.findByDynamicObject(data.dynamicObjectId, data.dynamicObjectVersion)
        
        val dataToSave = if (existing != null) {
            // 1. Check permissions
            val userShareInfo = existing.sharedWith.find { it.sharedWith == data.sharedBy }
            val isOwner = existing.sharedBy == data.sharedBy
            val canReshare = isOwner || (userShareInfo?.canReshare == true)

            if (!canReshare) {
                throw IllegalAccessException("User does not have permission to share this object")
            }

            // 2. Prepare new shares with correct originator (data.sharedBy)
            // and merge with existing list (avoiding duplicates for same target user)
            val existingSharedWithMap = existing.sharedWith.associateBy { it.sharedWith }.toMutableMap()
            
            data.sharedWith.forEach { newShare ->
                existingSharedWithMap[newShare.sharedWith] = newShare.copy(sharedBy = data.sharedBy)
            }

            existing.copy(sharedWith = existingSharedWithMap.values.toList())
        } else {
            // First time sharing: data.sharedBy is the owner
            data
        }

        return repository.save(dataToSave)
    }

    fun unshare(dynamicObjectId: String, version: Int, authenticatedUser: String): Boolean {
        val existing = repository.findByDynamicObject(dynamicObjectId, version) ?: return false

        return if (existing.sharedBy == authenticatedUser) {
            repository.delete(dynamicObjectId, version)
        } else {
            val userShare = existing.sharedWith.find { it.sharedWith == authenticatedUser }
            if (userShare != null) {
                val updatedSharedWith = existing.sharedWith.filter { it.sharedWith != authenticatedUser }
                val updated = existing.copy(sharedWith = updatedSharedWith)
                repository.save(updated)
                true
            } else {
                false
            }
        }
    }

    fun revokeShare(dynamicObjectId: String, version: Int, targetUser: String, authenticatedUser: String): Boolean {
        val existing = repository.findByDynamicObject(dynamicObjectId, version) ?: return false

        // Check if the authenticated user has permission to revoke shares
        val isOwner = existing.sharedBy == authenticatedUser
        val userShareInfo = existing.sharedWith.find { it.sharedWith == authenticatedUser }
        val canRevoke = isOwner || (userShareInfo?.canReshare == true)

        if (!canRevoke) {
            throw IllegalAccessException("User does not have permission to revoke shares")
        }

        val updatedSharedWith = existing.sharedWith.filter { it.sharedWith != targetUser }
        
        // If nothing changed (targetUser wasn't in the list), just return false
        if (updatedSharedWith.size == existing.sharedWith.size) return false

        val updated = existing.copy(sharedWith = updatedSharedWith)
        repository.save(updated)
        return true
    }

    fun getShares(dynamicObjectId: String, version: Int, authenticatedUser: String): SharedDynamicObject? {
        val shares = repository.findByDynamicObject(dynamicObjectId, version)
        return shares?.takeIf { s ->
            s.sharedBy == authenticatedUser || s.sharedWith.any { it.sharedWith == authenticatedUser }
        }
    }
}
