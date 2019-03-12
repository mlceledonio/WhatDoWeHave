package com.mesha.whatdowehave.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.mesha.whatdowehave.R
import java.lang.Exception
import java.util.*

class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val etExpiry: EditText = findViewById(R.id.et_expiration)
        etExpiry.setOnFocusChangeListener { v, hasFocus -> if(etExpiry.hasFocus()){
            etExpiry.showSoftInputOnFocus = false
            etExpiry.hideKeyboard()
            setDate(etExpiry)
        } }

        etExpiry.setOnClickListener{
            setDate(etExpiry)
        }


        val btn: View = findViewById(R.id.btn_accept)
        btn.setOnClickListener{
            saveItem()
        }
    }

    private fun setDate(et: EditText){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            et.setText("$dayOfMonth/$monthOfYear/$year")
        }, year, month, day)
        dpd.show()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    private fun saveItem(){
        val etNewItem: EditText = findViewById(R.id.et_itemName)
        val etNewQty: EditText = findViewById(R.id.et_quantity)
        val etNewExp: EditText = findViewById(R.id.et_expiration)

        try{

            val myDatabase = this.openOrCreateDatabase("item_list", Context.MODE_PRIVATE, null)
            val sqlInsert = "INSERT INTO item (item_name, quantity, expiration) VALUES (?, ?, ?)"
            val statement  = myDatabase.compileStatement(sqlInsert)

            statement.bindString(1,etNewItem.text.toString())
            statement.bindString(2,etNewQty.text.toString())
            statement.bindString(3,etNewExp.text.toString())
            statement.execute()
            myDatabase.close()

        }catch (e:Exception){
            e.printStackTrace()
        }

        val intent = intent
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
