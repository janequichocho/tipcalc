package edu.us.ischool.janeq97.tipcalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import android.util.Log
import java.math.BigDecimal
import java.math.RoundingMode


class MainActivity : AppCompatActivity() {
    var didFormat: Boolean = false
    var money: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tipBtn: Button = findViewById(R.id.tipBtn)
        val amount: EditText = findViewById(R.id.amount)

        tipBtn.isEnabled = false

        tipBtn.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                var tip = BigDecimal(money * 0.15).setScale(2, RoundingMode.HALF_EVEN)
                Toast.makeText(applicationContext, "Tip: $$tip", Toast.LENGTH_LONG).show()
            }
        })

        amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (amount.text.toString() != "") {
                    tipBtn.isEnabled = true
                }

                if (!didFormat) {
                    formatText(amount)
                } else {
                    didFormat = false
                }
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    fun formatText(num: EditText) {
        var text: String = num.text.toString()
        var formattedText = ""
        var onLeadingZeros = true
        for (i in 0..text.length - 1) {

            if (text[i].isDigit() && (text[i] != '0' || !onLeadingZeros)) {
                formattedText = formattedText + text[i]
                onLeadingZeros = false
            }
        }

        when (formattedText.length) {
            0 -> formattedText = "000"
            1 -> formattedText = "00" + formattedText
            2 -> formattedText = "0" + formattedText
            else -> {
                // do nothing
            }
        }

        formattedText = formattedText.substring(0, formattedText.length - 2) + "." +
                formattedText.substring(formattedText.length - 2)

        didFormat = true

        money = formattedText.toDouble()

        num.setText("$" + formattedText)
        num.setSelection(num.text.length)

    }
}
