package com.msnegi.mvvmtodoapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msnegi.mvvmtodoapp.CallBackInterface
import com.msnegi.mvvmtodoapp.R
import com.msnegi.mvvmtodoapp.RecyclerViewAdapter
import com.msnegi.mvvmtodoapp.databinding.ActivityMainBinding
import com.msnegi.mvvmtodoapp.roomdb.Item
import com.msnegi.mvvmtodoapp.roomdb.ItemRepository
import com.msnegi.mvvmtodoapp.viewmodel.MainViewModel

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.msnegi.mvvmtodoapp.roomdb.AppDatabase
import com.msnegi.mvvmtodoapp.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity(), CallBackInterface {

    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val repository = ItemRepository(this@MainActivity.application)

        mainViewModel = ViewModelProvider(this,
            MainViewModelFactory(repository)
        ).get(MainViewModel::class.java)

        mainViewModel.getItems().observe(this, Observer {
            updateViewOnAdd(it)
        })

        binding.addBtn.setOnClickListener {
            val item = Item()
            item.item_id = binding.titletxt.text.toString()
            item.descripton = binding.descriptiontxt.text.toString()
            mainViewModel.insertItem(item)
        }
    }

    fun updateViewOnAdd(taskItemArr: List<Item>) {
        if (taskItemArr != null) {
            showAllToDos(taskItemArr)
            clearEditTexts()
        } else {
            Toast.makeText(this, "Data Not Found", Toast.LENGTH_LONG).show()
        }
    }

    fun showAllToDos(taskItemsArr: List<Item>) {
        val adapter = RecyclerViewAdapter(taskItemsArr, this)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    private fun clearEditTexts() {
        binding.titletxt!!.setText("")
        binding.descriptiontxt!!.setText("")
    }

    override fun editItem(taskItem: Item?) {             //-- called from adapter through callback
        binding.titletxt!!.setText(taskItem!!.item_id)
        binding.descriptiontxt!!.setText(taskItem!!.descripton)
    }

    override fun deleteItem(taskItem: Item?) {           //-- called from adapter through callback
        val itemRepository = ItemRepository(this@MainActivity.application)
        itemRepository!!.delete(taskItem)
    }


}