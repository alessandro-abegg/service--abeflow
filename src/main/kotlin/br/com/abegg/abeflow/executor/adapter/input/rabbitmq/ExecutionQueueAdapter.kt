package br.com.abegg.abeflow.executor.adapter.input.rabbitmq

import br.com.abegg.abeflow.executor.application.ports.input.ExecuteScriptUseCase
import br.com.abegg.abeflow.executor.domain.model.ExecutionRequest
import br.com.abegg.abeflow.service.config.RabbitMQConfig
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class ExecutionQueueAdapter(
    private val executeScriptUseCase: ExecuteScriptUseCase
) {

    @RabbitListener(queues = [RabbitMQConfig.EXECUTION_QUEUE])
    fun onExecutionRequest(request: ExecutionRequest) {
        executeScriptUseCase.execute(request)
    }
}
