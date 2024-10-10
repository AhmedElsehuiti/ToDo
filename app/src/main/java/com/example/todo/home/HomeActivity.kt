package com.example.todo.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.home.activity.SettingActivity
import com.example.todo.home.fragments.AddTodoBottomSheet
import com.example.todo.home.fragments.TodoListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    private lateinit var navigationView : BottomNavigationView
    private lateinit var  add :FloatingActionButton
    val todoListFragment =TodoListFragment()
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navigationView= findViewById(R.id.navigationView)
        add = findViewById(R.id.add)
        add.setOnClickListener {
            showAddBottomSheet()
        }
        navigationView.setOnItemSelectedListener(
            NavigationBarView.OnItemSelectedListener {item->
                if (item.itemId==R.id.item_list){
                    pushFragment(todoListFragment)

                }else if (item.itemId==R.id.item_setting){
                    val intent =Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                   finish()
                }
                return@OnItemSelectedListener true
            }
        )
        navigationView.selectedItemId = R.id.item_list
    }
private fun pushFragment(fragment:Fragment){
    supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container,fragment)
        .commit()

}
private fun showAddBottomSheet(){
    val addBottomSheet=AddTodoBottomSheet()

    addBottomSheet.show(supportFragmentManager,"")
    addBottomSheet.onTodoAddedListener =object :AddTodoBottomSheet.OnTodoAddedListener{
        override fun onTodoAdded() {
            //refresh Todos list from database inside listFragment
            todoListFragment.getTodoListFromDB()
        }
    }
}
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (backPressedTime+3000>System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        }
        else {
            Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}