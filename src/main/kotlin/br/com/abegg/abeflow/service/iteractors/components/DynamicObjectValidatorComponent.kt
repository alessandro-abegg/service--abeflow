package br.com.abegg.abeflow.service.iteractors.components

import br.com.abegg.abeflow.service.entities.DynamicObject
import jakarta.xml.bind.ValidationException
import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult

@Component
class DynamicObjectValidatorComponent(
    private val objectMapper: tools.jackson.databind.ObjectMapper,
    private val validator: org.springframework.validation.Validator
) {
    fun validate(dynamicObject: DynamicObject) {
        val contentObject = try {
            objectMapper.convertValue(dynamicObject.content, dynamicObject.type.targetClass.java)
        } catch (e: IllegalArgumentException) {
            throw ValidationException("Payload structure does not match ${dynamicObject.type} requirements")
        }

        val errors = BeanPropertyBindingResult(contentObject, "content")

        validator.validate(contentObject, errors)

        if (errors.hasErrors()) {
            val detail = errors.allErrors.joinToString { it.defaultMessage ?: "Invalid field value" }
            throw ValidationException("Validation failed for: $detail")
        }
    }
}