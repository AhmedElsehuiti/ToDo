package com.example.todo.home.fragments

import android.annotation.SuppressLint

import android.content.Intent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.MyDataBase
import com.example.todo.database.model.Todo
import com.example.todo.home.activity.UpdateTodo
import com.example.todo.home.fragments.adapter.TodoRecyclerAdapter
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.Calendar

class TodoListFragment:Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val adapter =TodoRecyclerAdapter(null)
    private lateinit var calendarView:MaterialCalendarView
    lateinit var  todoList:MutableList<Todo>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        onTodoClick()
    }

    override fun onResume() {
        super.onResume()
        getTodoListFromDB()
    }
    private fun initView(){
        recyclerView=requireView().findViewById(R.id.todo_recyclerView)
        calendarView= requireView().findViewById(R.id.calendarView)
        calendarView.selectedDate= CalendarDay.today()
        recyclerView.adapter=adapter

        calendarView.setOnDateChangedListener { _, calenderDay, _ ->
            calender.set(Calendar.DAY_OF_MONTH,calenderDay.day)
            calender.set(Calendar.MONTH,calenderDay.month-1)
            calender.set(Calendar.YEAR,calenderDay.year)
            getTodoListFromDB()

        }
    }

    private var calender=Calendar.getInstance()
    fun getTodoListFromDB(){
         todoList = MyDataBase.getInstance(requireContext())
            .todoDao().getTodoByDate(calender.clearTime().time)
        adapter.changeData(todoList.toMutableList())
    }
    private fun onTodoClick(){
        adapter.onItemClickListener =object :TodoRecyclerAdapter.OnItemClickListener{
            override fun onItemClickToUpdate(todo: Todo) {
                val intent =Intent(requireContext(),UpdateTodo::class.java)
                intent.putExtra("todo",todo)
                startActivity(intent)
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClickToDelete(position: Int, todo: Todo) {
                todoList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.changeData(todoList)
                adapter.notifyDataSetChanged()
                MyDataBase.getInstance(requireContext()).todoDao().deleteTodo(todo)
                Toast.makeText(requireContext(),"item Removed",Toast.LENGTH_LONG).show()

            }
        }
    }




}