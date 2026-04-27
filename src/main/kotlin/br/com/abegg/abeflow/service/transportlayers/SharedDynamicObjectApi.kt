package br.com.abegg.abeflow.service.transportlayers

import br.com.abegg.abeflow.service.entities.SharedDynamicObject
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RequestMapping("/dynamic-object/share")
interface SharedDynamicObjectApi {

    @PostMapping("/{id}/version/{version}")
    @Operation(
        summary = "Share a dynamic object",
        description = "Shares a dynamic object with specified users. Only the owner or existing shared users can share."
    )
    fun share(
        @PathVariable id: String,
        @PathVariable version: Int,
        @RequestBody sharedWith: List<String>
    ): SharedDynamicObject

    @DeleteMapping("/{id}/version/{version}")
    @Operation(
        summary = "Unshare a dynamic object",
        description = "Removes sharing of a dynamic object. Only the owner can unshare."
    )
    fun unshare(
        @PathVariable id: String,
        @PathVariable version: Int
    ): Boolean

    @GetMapping("/{id}/version/{version}")
    @Operation(
        summary = "Get sharing info for a dynamic object",
        description = "Returns the sharing information for a dynamic object. Only the owner or shared users can access this."
    )
    fun getShares(
        @PathVariable id: String,
        @PathVariable version: Int
    ): SharedDynamicObject?
}
