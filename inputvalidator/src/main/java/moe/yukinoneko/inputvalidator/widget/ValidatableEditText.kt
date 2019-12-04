package moe.yukinoneko.inputvalidator.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import moe.yukinoneko.inputvalidator.R
import moe.yukinoneko.inputvalidator.Validator
import moe.yukinoneko.inputvalidator.interfaces.Validatable
import moe.yukinoneko.inputvalidator.model.Rule
import moe.yukinoneko.inputvalidator.model.ValidationError
import java.util.regex.Pattern

/**
 * Created by SamuelGjk on 2019/12/4.
 */
class ValidatableEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : EditText(context, attrs), Validatable {

    private val validator = Validator()

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ValidatableEditText)

        val notBlank = a.getBoolean(R.styleable.ValidatableEditText_validate_not_blank, false)
        val minLength = a.getInteger(R.styleable.ValidatableEditText_validate_min_length, -1)
        val maxLength = a.getInteger(R.styleable.ValidatableEditText_validate_max_length, -1)
        val regex = a.getString(R.styleable.ValidatableEditText_validate_regex)

        if (notBlank) {
            val notEmptyErrorMessage = a.getString(
                R.styleable.ValidatableEditText_validate_not_blank_error_message
            ) ?: context.getString(R.string.default_not_blank_error_message)

            validator.addRule(Rule(notEmptyErrorMessage) { it.isNotBlank() })
        }
        if (minLength > -1) {
            val minLengthErrorMessage = a.getString(
                R.styleable.ValidatableEditText_validate_min_length_error_message
            ) ?: context.getString(R.string.default_min_length_error_message, minLength)

            validator.addRule(Rule(minLengthErrorMessage) { it.length >= minLength })
        }
        if (maxLength > -1) {
            val maxLengthErrorMessage = a.getString(
                R.styleable.ValidatableEditText_validate_max_length_error_message
            ) ?: context.getString(R.string.default_max_length_error_message, maxLength)

            validator.addRule(Rule(maxLengthErrorMessage) { it.length <= maxLength })
        }
        if (regex != null) {
            val regexErrorMessage = a.getString(
                R.styleable.ValidatableEditText_validate_regex_error_message
            ) ?: context.getString(R.string.default_regex_error_message)

            validator.addRule(
                Rule(regexErrorMessage) {
                    Pattern.matches(regex, it)
                }
            )
        }

        a.recycle()
    }

    override fun getEditText(): EditText = this

    override fun addRule(rule: Rule): Validatable {
        validator.addRule(rule)
        return this
    }

    override fun getErrorMessages(): List<String> = validator.getErrorMessages(this)

    override fun validate(): Boolean = validator.validate(this)

    override fun validate(onPassed: () -> Unit, onFailed: (error: ValidationError) -> Unit) {
        validator.validate(this, onPassed, onFailed)
    }

    override fun validateWithShowError(): Boolean = validator.validateWithShowError(this)

    override fun validateWithShowError(
            onPassed: () -> Unit, onFailed: (error: ValidationError) -> Unit
    ) {
        validator.validateWithShowError(this, onPassed, onFailed)
    }
}