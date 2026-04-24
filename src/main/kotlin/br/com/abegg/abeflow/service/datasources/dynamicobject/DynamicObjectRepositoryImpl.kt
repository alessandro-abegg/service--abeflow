package br.com.abegg.abeflow.service.datasources.dynamicobject

import br.com.abegg.abeflow.service.datasources.dynamicobject.model.DynamicObjectModel
import br.com.abegg.abeflow.service.repositories.DynamicObjectRepository
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.stereotype.Component

@Component
class DynamicObjectRepositoryImpl(
    val dynamicObjectRepository: DynamicObjectRepository,
    val mongoTemplate: MongoTemplate
) : DynamicObjectRepository {
    fun query(): MutableList<DynamicObjectModel?> {
        val sort = Aggregation.sort(
            Sort.Direction.DESC,
            "isMain",
            "id.version"
        )

        val replacer = Aggregation.replaceRoot("document")
        val exclude = Aggregation.project().andExclude("content")
        val group = Aggregation.group("id.scriptId")
            .first("$\$ROOT").`as`("document")
        
        val agg = Aggregation.newAggregation(sort, group, replacer, exclude)

        return mongoTemplate
            .aggregate(
                agg,
                "dynamic_objects",
                DynamicObjectModel::class.java
            ).getMappedResults()
    }
}