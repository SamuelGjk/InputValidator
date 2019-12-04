package moe.yukinoneko.inputvalidator

import android.widget.EditText
import moe.yukinoneko.inputvalidator.interfaces.Validatable
import moe.yukinoneko.inputvalidator.model.Rule
import moe.yukinoneko.inputvalidator.model.ValidationError

/**
 * Created by SamuelGjk on 2019/12/3.
 */
class ValidationWrapper(private val editText: EditText) : Validatable {

    private val validator = Validator()

    override fun getEditText(): EditText = editText

    override fun addRule(rule: Rule): Validatable {
        validator.addRule(rule)
        return this
    }

    override fun getErrorMessages(): List<String> = validator.getErrorMessages(editText)

    override fun validate(): Boolean = validator.validate(editText)

    override fun validate(onPassed: () -> Unit, onFailed: (error: ValidationError) -> Unit) {
        validator.validate(editText, onPassed, onFailed)
    }

    override fun validateWithShowError(): Boolean = validator.validateWithShowError(editText)

    override fun validateWithShowError(
            onPassed: () -> Unit, onFailed: (error: ValidationError) -> Unit
    ) {
        validator.validateWithShowError(editText, onPassed, onFailed)
    }
}