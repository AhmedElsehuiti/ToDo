package com.example.todo.home.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.database.MyDataBase
import com.example.todo.database.model.Todo

import com.example.todo.databinding.ActivityUpdateTodoBinding

class UpdateTodo : AppCompatActivity() {
     private lateinit var activityUpdateTodoBinding: ActivityUpdateTodoBinding
    lateinit var todo: Todo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityUpdateTodoBinding = ActivityUpdateTodoBinding.inflate(layoutInflater)
        setContentView(activityUpdateTodoBinding.root)

        todo= (intent.getSerializableExtra("todo") as? Todo)!!
        //show Data
        showDate(todo)
        activityUpdateTodoBinding.change.setOnClickListener {
          updateTodo()

        }
    }
    private fun showDate(todo:Todo){
        activityUpdateTodoBinding.titleLayoutEd.editText!!.setText(todo.name)
        Log.e("date",todo.date.toString())
     //  activityUpdateTodoBinding.chooseDateEd.text = todo.date.toString()
        activityUpdateTodoBinding.detailsLayoutEd.editText!!.setText(todo.details)
    }
    private fun updateTodo(){

        if(validateFrom()){
            todo.name = activityUpdateTodoBinding.titleLayoutEd.editText!!.text.toString()
            todo.details = activityUpdateTodoBinding.detailsLayoutEd.editText!!.text.toString()
          //  todo.date?.time =activityUpdateTodoBinding.chooseDateEd.toString().toLong()
            MyDataBase.getInstance(this@UpdateTodo)
                .todoDao().updateTodo(todo)
            finish()
        }

    }
    private fun validateFrom():Boolean{
        var isValid = true
        if (activityUpdateTodoBinding.titleLayoutEd.editText?.text.toString().isBlank()){
            activityUpdateTodoBinding.titleLayoutEd.error ="please enter todo title"
            isValid=false
        }else{
            activityUpdateTodoBinding.titleLayoutEd.error=null
        }
        if (activityUpdateTodoBinding.detailsLayoutEd.editText?.text.toString().isBlank()){
            activityUpdateTodoBinding.detailsLayoutEd.error ="please enter todo details"
            isValid=false
        }else{
            activityUpdateTodoBinding.detailsLayoutEd.error = null
        }
        return isValid
    }

}