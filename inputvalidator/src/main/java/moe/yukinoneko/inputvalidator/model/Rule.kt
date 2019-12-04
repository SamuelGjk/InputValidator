package moe.yukinoneko.inputvalidator.model

import android.text.Editable

/**
 * Created by SamuelGjk on 2019/12/4.
 */
data class Rule(
    val errorMessage: String,
    val condition: (Editable) -> Boolean
)