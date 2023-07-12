package com.example.mykotlinbank

import android.graphics.Color
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mykotlinbank.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat

private var saldo: Int =0
private var saldoFormatted = NumberFormat.getNumberInstance().format(saldo)
private var hideSaldoText: Boolean = true
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btOk.setOnClickListener {
            operation()
        }

        binding.swSaldo.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){

                binding.swSaldo.setText("Ocultar Saldo [ON]")
                binding.swSaldo.setTextColor(ContextCompat.getColor(this,R.color.green))
                hideSaldoText= false
                binding.tvInfo.setText("Selecciona una opción")
                binding.tvInfo.setBackgroundColor(Color.WHITE)
                val colorGreen = ContextCompat.getColor(this,R.color.green)
                binding.tvInfo.setTextColor(colorGreen)
            }else{
                binding.swSaldo.setText("Ocultar Saldo [OFF]")
                binding.swSaldo.setTextColor(ContextCompat.getColor(this,R.color.red))
                hideSaldoText=true
            }
            showSaldo()
        }


    }

    fun operation(){
        val selectedRadioButtonId = binding.rg.checkedRadioButtonId
        if (selectedRadioButtonId != -1){
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            when (selectedRadioButton) {
                binding.rbSaldo -> showSaldo()

                binding.rbDepositar -> depositar()

                binding.rbRetirar -> retirar()

                binding.rbExit -> confirmarSalida()
            }
            binding.etnAmount.setText("")
        }else{
            Toast.makeText(this,"Selecciona una opción!",Toast.LENGTH_SHORT).show()
        }
    }


    fun retirar(){

        val retiro = binding.etnAmount.text.toString()
        if (retiro.isNotEmpty()){
            val retirar = retiro.toInt()
            giro(retirar)
        }else {
            Toast.makeText(this, "Ingrese un valor Valido!", Toast.LENGTH_SHORT).show()
        }
        showSaldo()
    }
    private fun updateSaldoFormatted() {
        saldoFormatted = NumberFormat.getNumberInstance().format(saldo)
    }
    fun giro(amount: Int){
        if (amount <= saldo){
            saldo -= amount
            updateSaldoFormatted()
            Toast.makeText(this, "Retiro Exitoso, tu nuevo saldo es de: $ ${saldoFormatted}", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this,"No tienes Saldo suficiente! tu saldo actual es de $ ${saldoFormatted}", Toast.LENGTH_SHORT).show()
        }

    }


    fun depositar(){
        val deposito = binding.etnAmount.text.toString()
        if (deposito.isNotEmpty()){
            val deposit= deposito.toInt()
            agregar(deposit)
        }else{
            Toast.makeText(this, "Ingrese un valor Valido!", Toast.LENGTH_SHORT).show()
        }
       showSaldo()
    }

    fun agregar(amount: Int){
        saldo += amount
        updateSaldoFormatted()
        Toast.makeText(this, "Nuevo deposito, tu saldo actual es de $ ${saldoFormatted}", Toast.LENGTH_SHORT).show()
    }

    fun showSaldo(){
        if (hideSaldoText == true){
        updateSaldoFormatted()
        binding.tvInfo.text="Tu saldo es: $ $saldoFormatted"
        binding.tvInfo.setBackgroundColor(Color.BLUE)
        binding.tvInfo.setTextColor(Color.WHITE)
        }else{
            Toast.makeText(this, "Tu saldo es: \$ $saldoFormatted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmarSalida() {
        val snackbar = Snackbar.make(binding.root, "¿Estás seguro de salir?", Snackbar.LENGTH_LONG)
        snackbar.setAction("Salir") {
            salir()
        }
        snackbar.show()
    }
    fun salir(){
        Toast.makeText(this, "Gracias por usar Kotlin Bank!", Toast.LENGTH_SHORT).show()
        finish()
    }

}

