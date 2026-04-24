package br.com.abegg.abeflow.service.datasources.dynamicobject

import br.com.abegg.abeflow.service.datasources.dynamicobject.mappers.toEntity
import br.com.abegg.abeflow.service.datasources.dynamicobject.mappers.toModel
import br.com.abegg.abeflow.service.datasources.dynamicobject.model.DynamicObjectKey
import br.com.abegg.abeflow.service.datasources.dynamicobject.model.DynamicObjectModel
import br.com.abegg.abeflow.service.entities.DynamicObject
import br.com.abegg.abeflow.service.repositories.DynamicObjectRepository
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class DynamicObjectRepositoryImpl(
    val dynamicObjectRepositoryMongo: DynamicObjectRepositoryMongo,
    val mongoTemplate: MongoTemplate
) : DynamicObjectRepository {
    override fun query(): List<DynamicObject> {
        val sort = Aggregation.sort(
            Sort.Direction.DESC,
            "isMain",
            "id.version"
        )

        val replacer = Aggregation.replaceRoot("document")
        val exclude = Aggregation.project().andExclude("content")
        val group = Aggregation.group("id.scriptId")
            .first($$$"$ROOT").`as`("document")

        val agg = Aggregation.newAggregation(sort, group, replacer, exclude)

        return mongoTemplate
            .aggregate(
                agg,
                "dynamic_objects",
                DynamicObjectModel::class.java
            ).getMappedResults()
            .map(DynamicObjectModel::toEntity)
    }

    override fun get(
        id: String,
        version: Integer
    ) = this.dynamicObjectRepositoryMongo.findByIdOrNull(DynamicObjectKey(id, version))?.toEntity()

    override fun save(
        data: DynamicObject
    ) = this.dynamicObjectRepositoryMongo.save(data.toModel()).toEntity()
}