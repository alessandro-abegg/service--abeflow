package br.com.abegg.abeflow.executor.iteractors

import br.com.abegg.abeflow.executor.entities.ExecutionRequest
import org.slf4j.LoggerFactory.getLogger
import org.springframework.stereotype.Service

@Service
class ExecuteScriptUseCase {
    private val logger = getLogger(ExecuteScriptUseCase::class.java)

    fun execute(request: ExecutionRequest) {
        logger.info("Executing script for object ${request.dynamicObjectId} version ${request.version}")

        logger.info("Execution completed for ${request.dynamicObjectId}")
    }
}
