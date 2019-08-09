package ru.vlabum.android.gb.xweather.ui.adapter

interface SimpleItemAdapter {
    fun onItemDelete(position: Int)
    fun onItemMove(fromPosition: Int, toPosition: Int)
}