package moe.yukinoneko.inputvalidator.internal

import android.widget.EditText
import moe.yukinoneko.inputvalidator.InputValidator
import moe.yukinoneko.inputvalidator.model.Rule
import moe.yukinoneko.inputvalidator.model.ValidationError

/**
 * Created by SamuelGjk on 2019/12/3.
 */
internal class Validator {

    private val rules = mutableListOf<Rule>()

    fun addRule(rule: Rule) {
        rules.add(rule)
    }

    fun getErrorMessages(editText: EditText): List<String> {
        return rules.filterNot { it.condition(editText.text) }.map { it.errorMessage }
    }

    fun validate(editText: EditText): Boolean {
        return rules.find { !it.condition(editText.text) } == null
    }

    fun validate(
        editText: EditText, onPassed: () -> Unit, onFailed: (error: ValidationError) -> Unit
    ) {
        val errorMessages = getErrorMessages(editText)
        if (errorMessages.isNullOrEmpty()) {
            onPassed()
        } else {
            onFailed(ValidationError(editText, errorMessages))
        }
    }

    fun validateWithErrorHandler(editText: EditText): Boolean {
        val failed = rules.find { !it.condition(editText.text) }
        failed?.let { InputValidator.defaultErrorHandler(editText, it.errorMessage) }
        return failed == null
    }

    fun validateWithErrorHandler(
            editText: EditText, onPassed: () -> Unit, onFailed: (error: ValidationError) -> Unit
    ) {
        rules.find { !it.condition(editText.text) }?.let {
            InputValidator.defaultErrorHandler(editText, it.errorMessage)
        }

        validate(editText, onPassed, onFailed)
    }
}