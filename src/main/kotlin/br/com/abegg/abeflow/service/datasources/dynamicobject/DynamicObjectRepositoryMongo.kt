package br.com.abegg.abeflow.service.datasources.dynamicobject

import br.com.abegg.abeflow.service.datasources.dynamicobject.model.DynamicObjectKey
import br.com.abegg.abeflow.service.datasources.dynamicobject.model.DynamicObjectModel
import org.springframework.data.mongodb.repository.MongoRepository

interface DynamicObjectRepositoryMongo : MongoRepository<DynamicObjectModel, DynamicObjectKey>