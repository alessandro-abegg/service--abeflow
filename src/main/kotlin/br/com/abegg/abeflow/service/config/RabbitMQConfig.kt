package br.com.abegg.abeflow.service.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tools.jackson.databind.json.JsonMapper

@Configuration
class RabbitMQConfig {

    companion object {
        const val PIPELINE_QUEUE = "pipeline.queue"
        const val EXECUTION_QUEUE = "execution.queue"
        const val NOTIFICATION_QUEUE = "notification.queue"
    }

    @Bean
    fun pipelineQueue(): Queue {
        return Queue(PIPELINE_QUEUE, true)
    }

    @Bean
    fun executionQueue(): Queue {
        return Queue(EXECUTION_QUEUE, true)
    }

    @Bean
    fun notificationQueue(): Queue {
        return Queue(NOTIFICATION_QUEUE, true)
    }

    @Bean
    fun messageConverter(jsonMapper: JsonMapper): MessageConverter {
        return JacksonJsonMessageConverter(jsonMapper)
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory, messageConverter: MessageConverter): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = messageConverter
        return rabbitTemplate
    }
}
