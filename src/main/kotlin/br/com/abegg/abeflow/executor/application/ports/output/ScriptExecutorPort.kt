package br.com.abegg.abeflow.executor.application.ports.output

import br.com.abegg.abeflow.executor.domain.model.ExecutionRequest

interface ScriptExecutorPort {
    fun executeScript(request: ExecutionRequest)
}
