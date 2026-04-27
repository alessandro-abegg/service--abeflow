package br.com.abegg.abeflow.service.transportlayers

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/dynamic-object/execute")
interface DynamicObjectExecutionApi {

    @PostMapping("/{id}/version/{version}")
    @Operation(
        summary = "Execute a dynamic object",
        description = "Triggers the execution of a dynamic object by sending the provided form data to the execution queue."
    )
    fun execute(
        @PathVariable id: String,
        @PathVariable version: Int,
        @RequestBody formData: Map<String, Any>
    )
}
