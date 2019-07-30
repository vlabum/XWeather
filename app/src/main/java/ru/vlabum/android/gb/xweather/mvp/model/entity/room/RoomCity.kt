package ru.vlabum.android.gb.xweather.mvp.model.entity.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vlabum.android.gb.xweather.mvp.model.entity.ICity

@Entity
class RoomCity : ICity {

    @NonNull
    @PrimaryKey
    private lateinit var name: String

    override fun getId(): String {
        return getName()
    }

    override fun setId(id: String) {
        setName(id)
    }

    override fun getName(): String {
        return name
    }

    override fun setName(name: String) {
        this.name = name
    }
}