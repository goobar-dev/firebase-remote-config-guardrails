import com.google.firebase.remoteconfig.Parameter
import com.google.firebase.remoteconfig.ParameterValue
import com.google.firebase.remoteconfig.ParameterValueType

val ValidatedParameters = mapOf<String, (Parameter) -> Boolean>(
    "blurry_image_threshold" to ::verifyBlurryImageThreshold
)

fun verifyBlurryImageThreshold(parameter: Parameter): Boolean {
    if (parameter.valueType != ParameterValueType.NUMBER) return false

    val parameterValue = parameter.defaultValue
    return try {
        if (parameterValue is ParameterValue.Explicit) {
            val value = parameterValue.value.toDouble()
            value in 0.0..5.0
        } else {
            false
        }
    } catch (error: java.lang.NumberFormatException) {
        false
    }
}