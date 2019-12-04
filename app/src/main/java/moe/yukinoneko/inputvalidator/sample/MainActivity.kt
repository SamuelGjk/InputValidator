package moe.yukinoneko.inputvalidator.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import moe.yukinoneko.inputvalidator.ValidatableCollections
import moe.yukinoneko.inputvalidator.ValidationWrapper
import moe.yukinoneko.inputvalidator.model.Rule
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // single
        buttonSingleWithResult.setOnClickListener {
            textView1.text = editText1.validateWithShowError().toString()
        }
        buttonSingleWithCallback.setOnClickListener {
            editText1.validateWithShowError(
                    { textView1.text = "true" },
                    { error -> textView1.text = error.messages.joinToString() }
            )
        }

        // collections
        buttonCollectionsWithResult.setOnClickListener {
            textView2.text = ValidatableCollections.validateWithShowError(editText2, editText3, editText4).toString()
        }
        buttonCollectionsWithCallback.setOnClickListener {
            textView2.text = ""
            ValidatableCollections.validateWithShowError(
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
        val wrapper1 = ValidationWrapper(editText5)
                .addRule(Rule("wrapper1: not blank") { it.isNotBlank() })
                .addRule(Rule("wrapper2: min length") { it.length >= 5 })
        val wrapper2 = ValidationWrapper(editText6).addRule(Rule("wrapper2: invalid") { Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", it) })
        buttonWrapperWithResult.setOnClickListener {
            textView3.text = ValidatableCollections.validateWithShowError(wrapper1, wrapper2).toString()
        }
        buttonWrapperWithCallback.setOnClickListener {
            textView3.text = ""
            ValidatableCollections.validateWithShowError(
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
