package com.msnegi.roomdbexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.msnegi.roomdbexample.CallBackInterface
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatButton
import android.os.Bundle
import com.msnegi.roomdbexample.R
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.msnegi.roomdbexample.RecyclerViewAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.msnegi.roomdbexample.roomdb.Item
import com.msnegi.roomdbexample.roomdb.ItemRepository

class MainActivity : AppCompatActivity(), CallBackInterface {

    var recyclerView: RecyclerView? = null
    var titletxt: AppCompatEditText? = null
    var descriptiontxt: AppCompatEditText? = null
    var addBtn: AppCompatButton? = null
    var taskRepository: ItemRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskRepository = ItemRepository(application)
        recyclerView = findViewById(R.id.recyclerview)
        titletxt = findViewById(R.id.titletxt)
        descriptiontxt = findViewById(R.id.descriptiontxt)
        addBtn = findViewById(R.id.addBtn)
        addBtn!!.setOnClickListener(View.OnClickListener {
            val title = titletxt!!.getText().toString()
            val description = descriptiontxt!!.getText().toString()
            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(description)) {
                Toast.makeText(this@MainActivity, "Fields are empty", Toast.LENGTH_SHORT).show()
            } else {
                val taskItem1 = taskRepository!!.findItemByItemId(title)
                if (taskItem1 != null) {
                    val taskItem = Item()
                    taskItem.id = taskItem1.id
                    taskItem.item_id = title
                    taskItem.descripton = description
                    taskRepository!!.update(taskItem)
                } else {
                    val taskItem = Item()
                    taskItem.item_id = title
                    taskItem.descripton = description
                    taskRepository!!.insert(taskItem)
                }
                clearEditTexts()
                val taskItemsArr: List<Item?> = taskRepository!!.getAll()
                showAllToDos(taskItemsArr)
            }
        })
        val taskItemsArr: List<Item?> = taskRepository!!.getAll()
        showAllToDos(taskItemsArr)
    }

    fun updateViewOnAdd(taskItemArr: List<Item?>?) {
        if (taskItemArr != null) {
            showAllToDos(taskItemArr)
            clearEditTexts()
        } else {
            Toast.makeText(this, "Data Not Found", Toast.LENGTH_LONG).show()
        }
    }

    fun showAllToDos(taskItemsArr: List<Item?>?) {
        val adapter = RecyclerViewAdapter(taskItemsArr, this)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun clearEditTexts() {
        titletxt!!.setText("")
        descriptiontxt!!.setText("")
    }

    override fun editItem(taskItem: Item?) {             //-- called from adapter through callback
        titletxt!!.setText(taskItem!!.item_id)
        descriptiontxt!!.setText(taskItem!!.descripton)
    }

    override fun deleteItem(taskItem: Item?) {           //-- called from adapter through callback
        taskRepository!!.delete(taskItem)
        val taskItemsArr = taskRepository!!.all
        updateViewOnAdd(taskItemsArr)
    }


}