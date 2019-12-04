package moe.yukinoneko.inputvalidator

import moe.yukinoneko.inputvalidator.interfaces.Validatable
import moe.yukinoneko.inputvalidator.model.ValidationError

/**
 * Created by SamuelGjk on 2019/12/4.
 */
object ValidatableCollections {

    @JvmStatic
    fun validate(vararg validations: Validatable): Boolean {
        return validate(validations.toList())
    }

    @JvmStatic
    fun validate(validations: List<Validatable>): Boolean {
        return validations.none { !it.validate() }
    }

    @JvmStatic
    fun validate(
            vararg validations: Validatable,
            onPassed: () -> Unit, onFailed: (errors: List<ValidationError>) -> Unit
    ) {
        validate(validations.toList(), onPassed, onFailed)
    }

    @JvmStatic
    fun validate(
            validations: List<Validatable>,
            onPassed: () -> Unit, onFailed: (errors: List<ValidationError>) -> Unit
    ) {
        val failedValidations = validations.filterNot { it.validate() }
        callback(failedValidations, onPassed, onFailed)
    }

    @JvmStatic
    fun validateWithShowError(vararg validations: Validatable): Boolean {
        return validateWithShowError(validations.toList())
    }

    @JvmStatic
    fun validateWithShowError(validations: List<Validatable>): Boolean {
        return validations.filterNot { it.validateWithShowError() }.isEmpty()
    }

    @JvmStatic
    fun validateWithShowError(
            vararg validations: Validatable,
            onPassed: () -> Unit, onFailed: (errors: List<ValidationError>) -> Unit
    ) {
        validateWithShowError(validations.toList(), onPassed, onFailed)
    }

    @JvmStatic
    fun validateWithShowError(
            validations: List<Validatable>,
            onPassed: () -> Unit, onFailed: (errors: List<ValidationError>) -> Unit
    ) {
        val failedValidations = validations.filterNot { it.validateWithShowError() }
        callback(failedValidations, onPassed, onFailed)
    }

    @JvmStatic
    private fun callback(
            failedValidations: List<Validatable>,
            onPassed: () -> Unit, onFailed: (errors: List<ValidationError>) -> Unit
    ) {
        if (failedValidations.isNullOrEmpty()) {
            onPassed()
        } else {
            onFailed(
                    failedValidations.map {
                        ValidationError(it.getEditText(), it.getErrorMessages())
                    }
            )
        }
    }
}