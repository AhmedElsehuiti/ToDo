package com.example.todo.home.fragments


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.todo.R
import com.example.todo.database.MyDataBase
import com.example.todo.database.model.Todo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class AddTodoBottomSheet:BottomSheetDialogFragment() {
    private lateinit var titleLayout :TextInputLayout
    private lateinit var detailsLayout:TextInputLayout
    private lateinit var chooseDate:TextView
    private lateinit var addTodo:Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_add,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
    private fun initViews(){
        titleLayout= requireView().findViewById(R.id.titleLayout)
        detailsLayout= requireView().findViewById(R.id.detailsLayout)
        chooseDate = requireView().findViewById(R.id.chooseDate)
        (""+calendar.get(Calendar.DAY_OF_MONTH)+"/"
                +(calendar.get(Calendar.MONTH)+1)+"/"
                +calendar.get(Calendar.YEAR)).also { chooseDate.text = it }


        addTodo= requireView().findViewById(R.id.add)
        chooseDate.setOnClickListener {
            showDatePicker()
        }
        addTodo.setOnClickListener {
         if (validateFrom()){
             //from is valid and insert Todos item
             val title = titleLayout.editText?.text.toString()
             val details = detailsLayout.editText?.text.toString()
             insertTodo(title,details)

         }
        }

    }
    private fun validateFrom():Boolean{
        var isValid = true
        if (titleLayout.editText?.text.toString().isBlank()){
            titleLayout.error ="please enter todo title"
            isValid=false
        }else{
            titleLayout.error=null
        }
        if (detailsLayout.editText?.text.toString().isBlank()){
            detailsLayout.error ="please enter todo details"
            isValid=false
        }else{
            detailsLayout.error = null
        }
        return isValid
    }

    private fun insertTodo(title:String, details:String){

        val todo = Todo(
            name = title,
            details = details,
            date = calendar.clearTime().time,
        )
        MyDataBase.getInstance(requireContext().applicationContext)
            .todoDao().addTodo(todo)
        Toast.makeText(requireContext(),"Todo Added Successfully",Toast.LENGTH_LONG)
            .show()
        //call back to activity to notify insertion
        onTodoAddedListener?.onTodoAdded()
        dismiss()
    }
    var onTodoAddedListener:OnTodoAddedListener?=null
    interface OnTodoAddedListener{
        fun onTodoAdded()

    }
    private val calendar =Calendar.getInstance()

    private fun showDatePicker(){
        val datePicker =DatePickerDialog(requireContext(),
            { _, year, month, day ->
                calendar.set(Calendar.DAY_OF_MONTH,day)
                calendar.set(Calendar.MONTH,month)
                calendar.set(Calendar.YEAR,year)
                chooseDate.text = "$day/${(month+1)}/$year"
            }
            ,calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()

    }

}
