package com.mesha.whatdowehave

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import java.lang.Exception

class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val btn: View = findViewById(R.id.btn_accept)
        btn.setOnClickListener{
            saveItem()
        }
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


        }catch (e:Exception){
            e.printStackTrace()
        }

        val intent = intent
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
