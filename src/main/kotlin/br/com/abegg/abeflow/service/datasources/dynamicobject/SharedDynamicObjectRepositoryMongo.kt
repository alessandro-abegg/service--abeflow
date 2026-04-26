package br.com.abegg.abeflow.service.datasources.dynamicobject

import br.com.abegg.abeflow.service.datasources.dynamicobject.model.SharedDynamicObjectModel
import org.springframework.data.mongodb.repository.MongoRepository

interface SharedDynamicObjectRepositoryMongo : MongoRepository<SharedDynamicObjectModel, String>
