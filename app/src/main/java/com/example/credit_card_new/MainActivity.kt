package com.example.credit_card_new

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardNumber = findViewById<EditText>(R.id.cardNumber)
        val expiryDate = findViewById<EditText>(R.id.expiryDate)
        val cvv = findViewById<EditText>(R.id.cvv)

        // Formatação do número do cartão
        cardNumber.addTextChangedListener(object : TextWatcher {
            private var currentText = ""
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != currentText) {
                    val cleaned = s.toString().replace(" ", "")
                    val formatted = StringBuilder()
                    for (i in cleaned.indices) {
                        if (i % 4 == 0 && i != 0) formatted.append(" ")
                        formatted.append(cleaned[i])
                    }
                    currentText = formatted.toString()
                    cardNumber.setText(currentText)
                    cardNumber.setSelection(currentText.length) // move o cursor para o final
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Formatação da data de validade (MM/AA)
        expiryDate.addTextChangedListener(object : TextWatcher {
            private var currentText = ""
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != currentText) {
                    val cleaned = s.toString().replace("/", "")
                    val formatted = StringBuilder()
                    for (i in cleaned.indices) {
                        if (i == 2) formatted.append("/")
                        formatted.append(cleaned[i])
                    }
                    currentText = formatted.toString()
                    expiryDate.setText(currentText)
                    expiryDate.setSelection(currentText.length) // move o cursor para o final
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Validação simples ao perder o foco do campo
        cardNumber.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateFields(cardNumber, expiryDate, cvv)
            }
        }

        expiryDate.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateFields(cardNumber, expiryDate, cvv)
            }
        }

        cvv.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateFields(cardNumber, expiryDate, cvv)
            }
        }
    }

    private fun validateFields(cardNumber: EditText, expiryDate: EditText, cvv: EditText) {
        val cardNumberText = cardNumber.text.toString().replace(" ", "")
        val expiryDateText = expiryDate.text.toString()
        val cvvText = cvv.text.toString()

        if (cardNumberText.length != 16) {
            cardNumber.error = "Número do cartão deve ter 16 dígitos"
        }

        if (!expiryDateText.matches(Regex("(0[1-9]|1[0-2])/[0-9]{2}"))) {
            expiryDate.error = "Data de validade deve estar no formato MM/AA"
        }

        if (cvvText.length != 3) {
            cvv.error = "CVV deve ter 3 dígitos"
        }

        if (cardNumber.error == null && expiryDate.error == null && cvv.error == null) {
            Toast.makeText(this, "Validação bem-sucedida!", Toast.LENGTH_SHORT).show()
        }
    }
}