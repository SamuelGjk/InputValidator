package moe.yukinoneko.inputvalidator.model

import android.widget.EditText

/**
 * Created by SamuelGjk on 2019/12/4.
 */
data class ValidationError(
    val editText: EditText,
    val messages: List<String>
)