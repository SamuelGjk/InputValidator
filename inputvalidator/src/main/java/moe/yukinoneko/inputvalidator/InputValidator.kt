package moe.yukinoneko.inputvalidator

import android.widget.EditText

/**
 * Created by SamuelGjk on 2019/12/13.
 */
typealias ErrorHandler = (editText: EditText, errorMessage: String) -> Unit

object InputValidator {

    internal var defaultErrorHandler: ErrorHandler = { editText, errorMessage ->
        if (errorMessage.isNotBlank()) {
            editText.error = errorMessage
        }
    }

    @JvmStatic
    fun setErrorHandler(errorHandler: ErrorHandler) {
        defaultErrorHandler = errorHandler
    }
}