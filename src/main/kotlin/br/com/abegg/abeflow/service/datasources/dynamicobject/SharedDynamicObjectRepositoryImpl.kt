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
        return sharedDynamicObjectRepositoryMongo.save(data.toModel()).toEntity()
    }

    override fun delete(dynamicObjectId: String, version: Integer): Boolean {
        val query = Query(
            Criteria("dynamicObjectId").`is`(dynamicObjectId)
                .and("dynamicObjectVersion").`is`(version)
        )
        return mongoTemplate.remove(query, SharedDynamicObjectModel::class.java).deletedCount > 0
    }
}
