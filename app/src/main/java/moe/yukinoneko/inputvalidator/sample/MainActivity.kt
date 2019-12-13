package moe.yukinoneko.inputvalidator.sample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import moe.yukinoneko.inputvalidator.CollectionsValidator
import moe.yukinoneko.inputvalidator.InputValidator
import moe.yukinoneko.inputvalidator.ValidatableWrapper
import moe.yukinoneko.inputvalidator.model.Rule
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        InputValidator.setErrorHandler { _, errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        // single
        buttonSingleWithResult.setOnClickListener {
            textView1.text = editText1.validateWithErrorHandler().toString()
        }
        buttonSingleWithCallback.setOnClickListener {
            editText1.validateWithErrorHandler(
                    { textView1.text = "true" },
                    { error -> textView1.text = error.messages.joinToString() }
            )
        }

        // collections
        buttonCollectionsWithResult.setOnClickListener {
            textView2.text = CollectionsValidator.validateWithErrorHandler(
                    editText2, editText3, editText4
            ).toString()
        }
        buttonCollectionsWithCallback.setOnClickListener {
            textView2.text = ""
            CollectionsValidator.validateWithErrorHandler(
                    editText2, editText3, editText4,
                    onPassed = { textView2.text = "true" },
                    onFailed = { errors ->
                        errors.forEach { error ->
                            textView2.append(error.editText.id.toString())
                            textView2.append("：")
                            textView2.append(error.messages.joinToString())
                            textView2.append("\n")
                        }
                    }
            )
        }

        // wrapper
        val wrapper1 = ValidatableWrapper(editText5)
                .addRule(Rule("wrapper1: not blank") { it.isNotBlank() })
                .addRule(Rule("wrapper2: min length") { it.length >= 5 })
        val wrapper2 = ValidatableWrapper(editText6).addRule(
                Rule("wrapper2: invalid") {
                    Pattern.matches(
                            "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", it
                    )
                })
        buttonWrapperWithResult.setOnClickListener {
            textView3.text = CollectionsValidator.validateWithErrorHandler(
                    wrapper1, wrapper2
            ).toString()
        }
        buttonWrapperWithCallback.setOnClickListener {
            textView3.text = ""
            CollectionsValidator.validateWithErrorHandler(
                    wrapper1, wrapper2,
                    onPassed = { textView3.text = "true" },
                    onFailed = { errors ->
                        errors.forEach { error ->
                            textView3.append(error.editText.id.toString())
                            textView3.append("：")
                            textView3.append(error.messages.joinToString())
                            textView3.append("\n")
                        }
                    }
            )
        }
    }
}
