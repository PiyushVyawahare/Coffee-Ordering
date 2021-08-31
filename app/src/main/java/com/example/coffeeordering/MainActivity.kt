package com.example.coffeeordering

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

public var count: Int = 1
public var price1: Int = 100
public var wprice: Int = 0
public var cprice: Int = 0
public var num: Int = 0
public var c1: Boolean = false
public var c2: Boolean = false
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val value = findViewById<TextView>(R.id.value)
        val total_price = findViewById<TextView>(R.id.total_price)
        value.setText(""+count)
        total_price.setText("\u20b9"+""+ price1)
    }

    fun increaseQuantity(view: View) {
        price1 += 100
        count++
        if(c1)
            price1 += wprice
        if(c2)
            price1 += cprice

        value.setText(""+count)
        total_price.setText("\u20b9"+ price1)
    }

    fun decreaseQuantity(view: View) {
        count--
        price1 -= 100
        if(c1)
            price1 -= wprice
        if(c2)
            price1 -= cprice
        if (count != 0) {
            value.setText("" + count)
            total_price.setText("\u20b9"+"" + price1)
        }
        else {
            Toast.makeText(this, "Select at least one coffee.", Toast.LENGTH_LONG).show()
            value.setText("" + count)
            total_price.setText("\u20b9"+"" + price1)
        }
    }

    fun addWheepCream(view: View) {
        if(view is CheckBox) {
            val check1: Boolean = view.isChecked
            if(check1){
                c1 = true
                wprice = 10
                price1 += (count* wprice)
                total_price.setText("\u20b9"+ price1)
            }
            else{
                c1 = false
                price1 -= (count* wprice)
                total_price.setText("\u20b9"+ price1)
            }
        }
    }

    fun addChocolate(view: View) {
        if(view is CheckBox) {
            val check2: Boolean = view.isChecked
            if(check2){
                c2 = true
                cprice = 20
                price1 += (count* cprice)
                total_price.setText("\u20b9"+ price1)
            }
            else{
                c2 = false
                price1 -= (count* cprice)
                total_price.setText("\u20b9"+ price1)
            }
        }
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)



        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }

    fun placeOrder(view: View) {
        val nameInput = findViewById<EditText>(R.id.nameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val wheepCream = findViewById<CheckBox>(R.id.wheepCream)
        val chocolate = findViewById<CheckBox>(R.id.chocolate)
        val check1: Boolean = wheepCream.isChecked
        val check2: Boolean = chocolate.isChecked
        val recipient : String = emailInput.editableText.toString()
        val subject: String = "Your order with Coffee Order is placed successfully!"
        var message = "Hi "+ nameInput.editableText.toString() +",\n"
        message += "\n Order summary : \n"
        message += "\n"+ count + "Coffees(\u20b9100) : \t \u20b9"+ (100 * count) + ".\n"
        if(check1)
            message += "Added wheepCream(\u20b910): \t \u20b9"+ (10 * count)+ ".\n"
        if(check2)
            message += "Added chocolate(\u20b920): \t \u20b9"+ (20 * count)+ ".\n"

        message += "\n Total Price to be paid : \t \u20b9"+ price1 + ".\n"

        message += "\n Your order will be in your hand in 2 min.\n"

        message += "Enjoy your coffee!\n"
        message += "\n Thank you for ordering with Coffee Order!"

        if(nameInput.editableText.isNotEmpty() and emailInput.editableText.isNotEmpty())
            sendEmail(recipient, subject, message)
        else
            Toast.makeText(this,"Please fill all details!", Toast.LENGTH_LONG).show()
    }

    fun ViewOrderSummary(view: View) {
        val nameInput = findViewById<EditText>(R.id.nameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val wheepCream = findViewById<CheckBox>(R.id.wheepCream)
        val chocolate = findViewById<CheckBox>(R.id.chocolate)
        val check1: Boolean = wheepCream.isChecked
        val check2: Boolean = chocolate.isChecked
        var message = "\n"+ count + " Coffees(\u20b9100) : \t \u20b9"+ (100 * count) + ".\n"
        if(check1)
            message += "Added wheepCream(\u20b910): \t \u20b9"+ (10 * count)+ ".\n"
        if(check2)
            message += "Added chocolate(\u20b920): \t \u20b9"+ (20 * count)+ ".\n"

        summary.setText(message)
    }

}






