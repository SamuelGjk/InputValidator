package moe.yukinoneko.inputvalidator

import android.widget.EditText
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

    fun validateWithShowError(editText: EditText): Boolean {
        val failed = rules.find { !it.condition(editText.text) }
        failed?.let {
            editText.error = it.errorMessage
        }
        return failed == null
    }

    fun validateWithShowError(
            editText: EditText, onPassed: () -> Unit, onFailed: (error: ValidationError) -> Unit
    ) {
        rules.find { !it.condition(editText.text) }?.let {
            editText.error = it.errorMessage
        }

        validate(editText, onPassed, onFailed)
    }
}