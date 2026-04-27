package br.com.abegg.abeflow.service.datasources.dynamicobjectexecution

import br.com.abegg.abeflow.lib.SecurityContext.getUserId
import br.com.abegg.abeflow.service.config.RabbitMQConfig
import br.com.abegg.abeflow.service.entities.ExecutionRequest
import br.com.abegg.abeflow.service.repositories.DynamicObjectExecutionRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class DynamicObjectExecutionRepositoryImpl(
    val rabbitTemplate: RabbitTemplate
) : DynamicObjectExecutionRepository {

    override fun execute(dynamicObjectId: String, version: Int, formData: Map<String, Any>) {
        val request = ExecutionRequest(
            dynamicObjectId = dynamicObjectId,
            version = version,
            formData = formData,
            authenticatedUser = getUserId()
        )

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXECUTION_QUEUE, request)
    }
}