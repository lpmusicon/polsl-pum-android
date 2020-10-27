package com.example.grzegorzmrozek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.math.BigDecimal

const val REAL = "com.example.grzegorzmrozek.REAL"
const val IMAGINARY = "com.example.grzegorzmrozek.IMG"

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
                this,
                R.array.spinner,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    public fun onPlusClick(view: View) {
        val aR: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO
        val aI: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal2).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO
        val bR: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal3).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO
        val bI: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal4).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO

        makeToast(additionString(aR, bR, aI, bI)).show()
        sendMessage(aR + bR, aI + bI)
    }

    public fun onMinusClick(view: View) {
        val aR: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO
        val aI: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal2).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO
        val bR: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal3).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO
        val bI: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal4).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO

        makeToast(substractionString(aR, bR, aI, bI)).show()
        sendMessage(aR - bR, aI - bI)
    }

    private fun makeToast(string: String): Toast {
        return Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT)
    }

    private fun additionString(aR: BigDecimal, bR: BigDecimal, aI: BigDecimal, bI: BigDecimal): String {
        return "${aR + bR} ${if ((aI + bI) >= BigDecimal.ZERO) '+' else '-'} ${(aI + bI).abs()}i"
    }

    private fun substractionString(aR: BigDecimal, bR: BigDecimal, aI: BigDecimal, bI: BigDecimal): String {
        return "${aR - bR} ${if ((aI - bI) >= BigDecimal.ZERO) '+' else '-'} ${(aI - bI).abs()}i"
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val aR: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO
        val aI: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal2).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO
        val bR: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal3).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO
        val bI: BigDecimal = findViewById<TextView>(R.id.editTextNumberDecimal4).text.toString().toBigDecimalOrNull()
                ?: BigDecimal.ZERO
        val item = parent.getItemAtPosition(pos).toString()

        (when (item) {
            "+" -> {
                sendMessage(aR + bR, aI + bI)
                makeToast(additionString(aR, bR, aI, bI))
            }
            "-" -> {
                sendMessage(aR - bR, aI - bI)
                makeToast(substractionString(aR, bR, aI, bI))
            }
            else -> null
        })?.show()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}

    fun sendMessage(real: BigDecimal, imaginary: BigDecimal) {
        val intent = Intent(this, GraphActivity::class.java).apply {
            putExtra(REAL, real.toDouble())
            putExtra(IMAGINARY, imaginary.toDouble())
        }

        startActivity(intent)
    }

}