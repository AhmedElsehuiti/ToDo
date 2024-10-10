package com.example.todo.home.fragments

import java.util.Calendar

fun Calendar.clearTime():Calendar{
    this.clear(Calendar.HOUR)
    this.clear(Calendar.MINUTE)
    this.clear(Calendar.SECOND)
    this.clear(Calendar.MILLISECOND)
    return this
}