package br.com.abegg.abeflow.executor.application.usecases

import br.com.abegg.abeflow.executor.application.ports.input.ExecuteScriptUseCase
import br.com.abegg.abeflow.executor.application.ports.output.ScriptExecutorPort
import br.com.abegg.abeflow.executor.domain.model.ExecutionRequest
import org.springframework.stereotype.Service

@Service
class ExecuteScriptUseCaseImpl(
    private val scriptExecutorPort: ScriptExecutorPort
) : ExecuteScriptUseCase {

    override fun execute(request: ExecutionRequest) {
        scriptExecutorPort.executeScript(request)
    }
}
