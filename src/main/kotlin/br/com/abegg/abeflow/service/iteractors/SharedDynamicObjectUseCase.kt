package br.com.abegg.abeflow.service.iteractors

import br.com.abegg.abeflow.executor.entities.ExecutionRequest
import br.com.abegg.abeflow.lib.SecurityContext.getUserId
import br.com.abegg.abeflow.service.config.RabbitMQConfig
import br.com.abegg.abeflow.service.entities.SharedDynamicObject
import br.com.abegg.abeflow.service.repositories.SharedDynamicObjectRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class SharedDynamicObjectUseCase(
    private val repository: SharedDynamicObjectRepository,
    private val rabbitTemplate: RabbitTemplate
) {

    fun share(data: SharedDynamicObject): SharedDynamicObject {
        require(data.dynamicObjectId.isNotBlank()) { "DynamicObjectId cannot be blank" }
        require(data.sharedBy.isNotBlank()) { "SharedBy (originator) cannot be blank" }

        val existing = repository.findByDynamicObject(data.dynamicObjectId, data.dynamicObjectVersion)

        val dataToSave = if (existing != null) {
            val userShareInfo = existing.sharedWith.find { it.sharedWith == data.sharedBy }
            val isOwner = existing.sharedBy == data.sharedBy
            val canReshare = isOwner || (userShareInfo?.canReshare == true)

            if (!canReshare) {
                throw IllegalAccessException("User does not have permission to share this object")
            }

            val existingSharedWithMap = existing.sharedWith.associateBy { it.sharedWith }.toMutableMap()

            data.sharedWith.forEach { newShare ->
                existingSharedWithMap[newShare.sharedWith] = newShare.copy(sharedBy = data.sharedBy)
            }

            existing.copy(sharedWith = existingSharedWithMap.values.toList())
        } else {
            data
        }

        return repository.save(dataToSave)
    }

    fun unshare(dynamicObjectId: String, version: Int): Boolean {
        val existing = repository.findByDynamicObject(dynamicObjectId, version) ?: return false

        val authenticatedUser = getUserId()

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

    fun revokeShare(dynamicObjectId: String, version: Int, targetUser: String): Boolean {
        val existing = repository.findByDynamicObject(dynamicObjectId, version) ?: return false

        val authenticatedUser = getUserId()

        val isOwner = existing.sharedBy == authenticatedUser
        val userShareInfo = existing.sharedWith.find { it.sharedWith == authenticatedUser }
        val canRevoke = isOwner || (userShareInfo?.canReshare == true)

        if (!canRevoke) {
            throw IllegalAccessException("User does not have permission to revoke shares")
        }

        val updatedSharedWith = existing.sharedWith.filter { it.sharedWith != targetUser }
        if (updatedSharedWith.size == existing.sharedWith.size) return false

        val updated = existing.copy(sharedWith = updatedSharedWith)
        repository.save(updated)
        return true
    }

    fun getShares(dynamicObjectId: String, version: Int): SharedDynamicObject? {
        val shares = repository.findByDynamicObject(dynamicObjectId, version)
        val authenticatedUser = getUserId()

        return shares?.takeIf { s ->
            s.sharedBy == authenticatedUser || s.sharedWith.any { it.sharedWith == authenticatedUser }
        }
    }

    fun execute(dynamicObjectId: String, version: Int, formData: Map<String, Any>) {
        val request = ExecutionRequest(
            dynamicObjectId = dynamicObjectId,
            version = version,
            formData = formData,
            authenticatedUser = getUserId()
        )
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXECUTION_QUEUE, request)
    }
}
