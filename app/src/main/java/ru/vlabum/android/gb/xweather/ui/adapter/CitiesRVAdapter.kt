package ru.vlabum.android.gb.xweather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.city_item.view.*
import ru.vlabum.android.gb.xweather.R
import ru.vlabum.android.gb.xweather.mvp.presenter.ICityListPresenter
import ru.vlabum.android.gb.xweather.mvp.view.ICityRowView
import java.util.*

class CitiesRVAdapter(var listCities: ICityListPresenter) : RecyclerView.Adapter<CitiesRVAdapter.ViewHolderI>(),
    SimpleItemAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderI {
        return ViewHolderI(LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listCities.getCount()
    }

    override fun onBindViewHolder(holder: ViewHolderI, position: Int) {
        holder.setPos(position)
        listCities.bind(holder)
        holder.itemView.clicks().map { o -> holder }.subscribe(listCities.getClickSubject())
    }

    override fun onItemDelete(position: Int) {
        //TODO сделать удаление
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        //TODO сделать перемещение
        notifyItemMoved(fromPosition, toPosition)
    }

    class ViewHolderI(itemView: View) : RecyclerView.ViewHolder(itemView), ICityRowView {

        private var pos: Int = 0

        override fun getPos(): Int {
            return pos
        }

        fun setPos(pos: Int) {
            this.pos = pos
        }

        override fun setName(name: String) {
            itemView.city_name_tv.text = name
        }
    }

}