package ru.vlabum.android.gb.xweather.mvp.model.entity

class City() : ICity {

    constructor(name: String) : this() {
        this.name = name
    }

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