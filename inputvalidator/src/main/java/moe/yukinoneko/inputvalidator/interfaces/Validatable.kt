package moe.yukinoneko.inputvalidator.interfaces

import android.widget.EditText
import moe.yukinoneko.inputvalidator.model.Rule
import moe.yukinoneko.inputvalidator.model.ValidationError

/**
 * Created by SamuelGjk on 2019/12/4.
 */
interface Validatable {
    fun getEditText(): EditText

    fun addRule(rule: Rule): Validatable

    fun getErrorMessages(): List<String>

    fun validate(): Boolean

    fun validate(onPassed: () -> Unit, onFailed: (error: ValidationError) -> Unit)

    fun validateWithShowError(): Boolean

    fun validateWithShowError(onPassed: () -> Unit, onFailed: (error: ValidationError) -> Unit)
}