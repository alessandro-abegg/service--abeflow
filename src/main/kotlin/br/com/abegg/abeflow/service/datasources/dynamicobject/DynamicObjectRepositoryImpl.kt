package br.com.abegg.abeflow.service.datasources.dynamicobject

import br.com.abegg.abeflow.service.datasources.dynamicobject.mappers.toEntity
import br.com.abegg.abeflow.service.datasources.dynamicobject.mappers.toModel
import br.com.abegg.abeflow.service.datasources.dynamicobject.model.DynamicObjectKey
import br.com.abegg.abeflow.service.datasources.dynamicobject.model.DynamicObjectModel
import br.com.abegg.abeflow.service.datasources.dynamicobject.model.SharedDynamicObjectModel
import br.com.abegg.abeflow.service.entities.DynamicObject
import br.com.abegg.abeflow.service.repositories.DynamicObjectRepository
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class DynamicObjectRepositoryImpl(
    val dynamicObjectRepositoryMongo: DynamicObjectRepositoryMongo,
    val mongoTemplate: MongoTemplate
) : DynamicObjectRepository {

    override fun query(authenticatedUser: String): List<DynamicObject> {
        // Get all shared dynamic object ids and versions
        val sharedObjects = mongoTemplate.find(
            Query(Criteria("sharedWith").`in`(authenticatedUser)),
            SharedDynamicObjectModel::class.java
        ).map { it.dynamicObjectId to it.dynamicObjectVersion }

        val sharedIds = sharedObjects.map { it.first }.toSet()
        val sharedVersions = sharedObjects.map { it.second }.toSet()

        val accessCriteria = Criteria().orOperator(
            Criteria("createdBy").`is`(authenticatedUser),
            Criteria("isPublished").`is`(true),
            Criteria().andOperator(
                Criteria("id.id").`in`(sharedIds),
                Criteria("id.version").`in`(sharedVersions)
            )
        )

        val sortByMainAndVersion = Aggregation.sort(
            Sort.Direction.DESC,
            "isMain",
            "id.version"
        )

        val matchAccessCriteria = Aggregation.match(accessCriteria)
        val replaceWithDocument = Aggregation.replaceRoot("document")
        val excludeContentField = Aggregation.project().andExclude("content")
        val groupByScriptId = Aggregation.group("id.scriptId")
            .first($$"$ROOT").`as`("document")

        val aggregationPipeline = Aggregation.newAggregation(
            sortByMainAndVersion,
            groupByScriptId,
            matchAccessCriteria,
            replaceWithDocument,
            excludeContentField
        )

        return mongoTemplate
            .aggregate(
                aggregationPipeline,
                "dynamic_objects",
                DynamicObjectModel::class.java
            ).getMappedResults()
            .map(DynamicObjectModel::toEntity)
    }

    override fun get(
        id: String,
        version: Integer,
        authenticatedUser: String
    ): DynamicObject? {
        val model = this.dynamicObjectRepositoryMongo.findByIdOrNull(DynamicObjectKey(id, version))
            ?: return null

        // Check access: user is creator OR object is published OR object is shared with user
        val isShared = mongoTemplate.exists(
            Query(
                Criteria("dynamicObjectId").`is`(id)
                    .and("dynamicObjectVersion").`is`(version)
                    .and("sharedWith").`in`(authenticatedUser)
            ),
            SharedDynamicObjectModel::class.java
        )

        val hasAccess = model.createdBy == authenticatedUser || model.isPublished || isShared
        if (!hasAccess) return null

        return model.toEntity()
    }

    override fun save(
        data: DynamicObject
    ) = this.dynamicObjectRepositoryMongo.save(data.toModel()).toEntity()
}