package ru.vlabum.android.gb.xweather.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_add_city.*
import ru.vlabum.android.gb.xweather.R
import java.lang.ClassCastException
import java.util.zip.Inflater




class AddCityFragmentDialog : DialogFragment() {

    interface AddCityDialogListener {
        fun onDialogPositiveClick(text: String?)
    }

    lateinit var listener: AddCityDialogListener

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        try {
            listener = activity as AddCityDialogListener
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var text: String? = null
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater? = activity?.layoutInflater
        builder.setView(inflater?.inflate(R.layout.fragment_add_city, null))
            .setPositiveButton(R.string.addCityOK, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val f = dialog as Dialog
                    val et = f.findViewById<EditText>(R.id.add_city_et)
                    text = et.text.toString()
                    listener.onDialogPositiveClick(text)
                }
            })
            .setNegativeButton(R.string.addCityCancel, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    getDialog().cancel()
                }
            })
        return builder.create()
    }
}