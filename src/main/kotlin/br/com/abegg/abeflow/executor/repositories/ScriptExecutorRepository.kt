package br.com.abegg.abeflow.executor.repositories

import br.com.abegg.abeflow.executor.entities.ExecutionRequest

interface ScriptExecutorRepository {
    fun executeScript(request: ExecutionRequest)
}