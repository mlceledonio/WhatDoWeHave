package com.mesha.whatdowehave.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
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
            var modifiedMonth = monthOfYear + 1
            et.setText("$dayOfMonth/$modifiedMonth/$year")
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

            val newItem = etNewItem.text.toString().toLowerCase()
            val newQty = etNewQty.text.toString()
            val newExp = etNewExp.text.toString()

            if(newItem.isBlank() || newItem.isEmpty()){
                Toast.makeText(this, "Item Name is required", Toast.LENGTH_LONG).show()
            } else if (newQty.isBlank() || newQty.isEmpty() || newQty.equals("0")){
                Toast.makeText(this, "Quantity is required", Toast.LENGTH_LONG).show()
            } else {
                val myDatabase = this.openOrCreateDatabase("item_list", Context.MODE_PRIVATE, null)
                val sqlSelect = "SELECT item_id, quantity FROM item WHERE item_name = '$newItem' AND expiration = '$newExp'"
                val selectResult = myDatabase.rawQuery(sqlSelect, null)

                selectResult.moveToFirst()

                if(selectResult.count == 0){

                    Log.d("AddItem", "selectResult is zero")

                    val sqlInsert = "INSERT INTO item (item_name, quantity, expiration) VALUES (?, ?, ?)"
                    val statement  = myDatabase.compileStatement(sqlInsert)

                    statement.bindString(1,newItem.toString())
                    statement.bindString(2,newQty.toString())
                    statement.bindString(3,newExp.toString())
                    statement.execute()
                } else {

                    Log.d("AddItemDebug", "selectResult is not zero")
                    val itemId = selectResult.getInt(selectResult.getColumnIndex("item_id"))
                    Log.d("AddItemDebug", "itemId is " + itemId)
                    val updatedQuantity = selectResult.getInt(selectResult.getColumnIndex("quantity")) + newQty.toString().toInt()
                    Log.d("AddItemDebug", "updatedQuantity is " + updatedQuantity)
                    val sqlUpdate = "UPDATE item SET quantity = $updatedQuantity WHERE item_id = $itemId"
                    myDatabase.execSQL(sqlUpdate)
                }


                myDatabase.close()
                val intent = intent
                setResult(Activity.RESULT_OK, intent)
                finish()
            }



        }catch (e:Exception){
            e.printStackTrace()
        }


    }
}
