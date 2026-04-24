package br.com.abegg.abeflow.service.entities

import org.springframework.data.annotation.Id

data class DynamicObject(
    @Id
    val id: String,
    val name: String,
    val description: String,
    val type: ObjectType,
    val content: String
)