package br.com.abegg.abeflow.executor.adapter.output.script

import br.com.abegg.abeflow.executor.application.ports.output.ScriptExecutorPort
import br.com.abegg.abeflow.executor.domain.model.ExecutionRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DefaultScriptExecutorAdapter : ScriptExecutorPort {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun executeScript(request: ExecutionRequest) {
        logger.info("Executing script for object ${request.dynamicObjectId} version ${request.version}")
        
        logger.info("Execution completed for ${request.dynamicObjectId}")
    }
}
