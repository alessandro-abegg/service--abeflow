package br.com.abegg.abeflow.service.transportlayers

import br.com.abegg.abeflow.service.entities.DynamicObject
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/dynamic-object")
interface DynamicObjectApi {
    @GetMapping("/query")
    @Operation(
        summary = "Query all dynamic objects",
        description = "Returns a list of all dynamic objects in the system."
    )
    fun query(): List<DynamicObject>

    @GetMapping("/{id}/version/{version}")
    @Operation(
        summary = "Get a dynamic object by ID and version",
        description = "Returns a specific dynamic object based on its ID and version. If the object does not exist, it returns null."
    )
    fun get(id: String, version: Integer): DynamicObject?

    @PostMapping
    @Operation(
        summary = "Save a dynamic object",
        description = "Saves a dynamic object to the system. If the object already exists, it updates it; otherwise, it creates a new entry. Returns the saved dynamic object."
    )
    fun save(data: DynamicObject): DynamicObject
}