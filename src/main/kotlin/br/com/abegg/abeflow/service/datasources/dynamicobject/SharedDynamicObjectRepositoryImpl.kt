package br.com.abegg.abeflow.service.datasources.dynamicobject

import br.com.abegg.abeflow.service.datasources.dynamicobject.mappers.toEntity
import br.com.abegg.abeflow.service.datasources.dynamicobject.mappers.toModel
import br.com.abegg.abeflow.service.datasources.dynamicobject.model.SharedDynamicObjectModel
import br.com.abegg.abeflow.service.entities.SharedDynamicObject
import br.com.abegg.abeflow.service.repositories.SharedDynamicObjectRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component

@Component
class SharedDynamicObjectRepositoryImpl(
    val sharedDynamicObjectRepositoryMongo: SharedDynamicObjectRepositoryMongo,
    val mongoTemplate: MongoTemplate
) : SharedDynamicObjectRepository {

    override fun findByDynamicObject(dynamicObjectId: String, version: Integer): SharedDynamicObject? {
        val query = Query(
            Criteria("dynamicObjectId").`is`(dynamicObjectId)
                .and("dynamicObjectVersion").`is`(version)
        )
        val model = mongoTemplate.findOne(query, SharedDynamicObjectModel::class.java)
        return model?.toEntity()
    }

    override fun save(data: SharedDynamicObject): SharedDynamicObject {
        val existing = findByDynamicObject(data.dynamicObjectId, data.dynamicObjectVersion)
        val dataToSave = if (existing != null) {
            // Allow if the user is the owner or already shared with
            if (existing.sharedBy != data.sharedBy && data.sharedBy !in existing.sharedWith) {
                throw IllegalAccessException("Only the owner or shared users can manage shares")
            }
            // Keep the original owner
            data.copy(sharedBy = existing.sharedBy)
        } else {
            data
        }
        return sharedDynamicObjectRepositoryMongo.save(dataToSave.toModel()).toEntity()
    }

    override fun unshare(dynamicObjectId: String, version: Integer, authenticatedUser: String): Boolean {
        val existing = findByDynamicObject(dynamicObjectId, version) ?: return false

        return if (existing.sharedBy == authenticatedUser) {
            // Owner: delete the entire share
            val query = Query(
                Criteria("dynamicObjectId").`is`(dynamicObjectId)
                    .and("dynamicObjectVersion").`is`(version)
            )
            mongoTemplate.remove(query, SharedDynamicObjectModel::class.java).deletedCount > 0
        } else if (authenticatedUser in existing.sharedWith) {
            // Shared user: remove themselves from the list
            val updatedSharedWith = existing.sharedWith - authenticatedUser
            val updated = existing.copy(sharedWith = updatedSharedWith)
            save(updated)
            true
        } else {
            false
        }
    }
}
