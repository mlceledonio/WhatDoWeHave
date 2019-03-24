package com.mesha.whatdowehave.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.mesha.whatdowehave.R
import java.lang.Exception
import java.util.*

class UpdateItemActivity : AppCompatActivity() {



    var oldItemName: String = ""
    var oldQuantity: Int = 0
    var oldExpiration: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_item)

        val etUpdateItemName: EditText = findViewById(R.id.et_update_itemName)
        val etUpdateQuantity: EditText = findViewById(R.id.et_update_quantity)
        val etUpdateExpiration: EditText = findViewById(R.id.et_update_expiration)

        oldItemName = intent.getStringExtra("KEY_ITEM_NAME")
        oldQuantity = intent.getIntExtra("KEY_QUANTITY", 0)
        oldExpiration = intent.getStringExtra("KEY_EXPIRATION")

        etUpdateItemName.setText(oldItemName)
        etUpdateQuantity.setText(oldQuantity.toString())
        etUpdateExpiration.setText(oldExpiration)

        etUpdateExpiration.setOnFocusChangeListener{v, hasFocus -> if(etUpdateExpiration.hasFocus()){
            etUpdateExpiration.showSoftInputOnFocus = false
            etUpdateExpiration.hideKeyboard()
            setDate(etUpdateExpiration)
        }}

        etUpdateExpiration.setOnClickListener{
            setDate(etUpdateExpiration)
        }

        val updateBtn: Button = findViewById(R.id.btn_update_confirm)
        updateBtn.setOnClickListener {
            updateItem()
        }
    }

    fun updateItem(){

        val etUpdateItemName: EditText = findViewById(R.id.et_update_itemName)
        val etUpdateQuantity: EditText = findViewById(R.id.et_update_quantity)
        val etUpdateExpiration: EditText = findViewById(R.id.et_update_expiration)

        val newItemName = etUpdateItemName.text
        val newQuantity = etUpdateQuantity.text
        val newExpiration = etUpdateExpiration.text

        try{
            val myDatabase = this.openOrCreateDatabase("item_list", Context.MODE_PRIVATE, null)
            val sqlSelect = "SELECT item_id, quantity FROM item WHERE item_name = '$newItemName' AND expiration = '$newExpiration'"
            val selectResult = myDatabase.rawQuery(sqlSelect, null)
            selectResult.moveToFirst()
            if(selectResult.count == 1){
                //TODO
            }

            val sqlUpdate = "UPDATE item SET item_name = '$newItemName', quantity = $newQuantity, expiration = '$newExpiration' WHERE item_name = '$oldItemName' AND quantity = $oldQuantity AND expiration = '$oldExpiration'"
            myDatabase.execSQL(sqlUpdate)
            myDatabase.close()

        }catch (e: Exception){
            e.printStackTrace()
        }

        val intent = intent
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
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
}
