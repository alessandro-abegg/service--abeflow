package br.com.abegg.abeflow.service.entities

import br.com.abegg.abeflow.service.entities.pojos.IPojo
import br.com.abegg.abeflow.service.entities.pojos.PipelinePojo
import br.com.abegg.abeflow.service.entities.pojos.ScriptPojo
import kotlin.reflect.KClass

enum class DynamicObjectType(val targetClass: KClass<out IPojo>) {
    SCRIPT(ScriptPojo::class),
    PIPELINE(PipelinePojo::class);
}
