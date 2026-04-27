package br.com.abegg.abeflow.service.repositories

interface DynamicObjectExecutionRepository {
    fun execute(dynamicObjectId: String, version: Int, formData: Map<String, Any>)
}