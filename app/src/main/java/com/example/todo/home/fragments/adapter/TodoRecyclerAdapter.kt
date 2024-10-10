package com.example.todo.home.fragments.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.model.Todo

class TodoRecyclerAdapter(private var items:MutableList<Todo>?):RecyclerView.Adapter<TodoRecyclerAdapter.SettingAdapter>() {
    class SettingAdapter(itemView:View):RecyclerView.ViewHolder(itemView){
        val title :TextView=itemView.findViewById(R.id.title_todo)
        val description:TextView=itemView.findViewById(R.id.description_todo)
        val markAsDone:ImageView=itemView.findViewById(R.id.mark_as_done)
        val rightView:ImageView=itemView.findViewById(R.id.right_view)
        val cardView :CardView=itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingAdapter {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo,parent,false)
        return SettingAdapter(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?:0
    }

    override fun onBindViewHolder(holder: SettingAdapter, position: Int) {
        val item = items!![position]
        holder.title.text =item.name
        holder.description.text=item.details
        if(onItemClickListener!=null){
            holder.cardView.setOnClickListener {
                onItemClickListener?.onItemClickToUpdate(items!![position])
            }
            holder.rightView.setOnClickListener {
                onItemClickListener?.onItemClickToDelete(position, item)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun changeData(newItems:MutableList<Todo>){
        items=newItems
        notifyDataSetChanged()
    }
    var onItemClickListener:OnItemClickListener?=null

    interface OnItemClickListener{
        fun onItemClickToUpdate(todo:Todo)
        fun onItemClickToDelete(position: Int,todo:Todo)

    }
}