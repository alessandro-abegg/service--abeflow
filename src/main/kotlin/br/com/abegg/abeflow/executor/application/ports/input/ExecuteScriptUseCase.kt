package br.com.abegg.abeflow.executor.application.ports.input

import br.com.abegg.abeflow.executor.domain.model.ExecutionRequest

interface ExecuteScriptUseCase {
    fun execute(request: ExecutionRequest)
}
