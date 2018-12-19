package com.mauricio.simplechoicedialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import java.io.Serializable
import java.util.*

/**
 * @author mauricio.barbosa
 *
 * Essa Dialog tem por objetivo exibir uma lista de items para que possa ser selecionado apenas uma
 * opção. Cada item exibido possui título e subtítulo. Ao confirmar a seleção, o indice do item selecionado
 * na lista será retornado a um listener informado previamente.
 *
 * {@see SingleChoiceDialogData}
 *
 */
class SingleChoiceDialog: androidx.fragment.app.DialogFragment()  {

    companion object {
        const val DATA_ARG = "DATA_ARG"

        fun newInstance(dataSingleChoice: SingleChoiceDialogData) : SingleChoiceDialog {
            val fragment = SingleChoiceDialog()
            val bundle = Bundle()
            bundle.putSerializable(DATA_ARG, dataSingleChoice)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var data: SingleChoiceDialogData
    private var selected: Int = -1

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        arguments?.let {
            data = it.getSerializable(DATA_ARG) as SingleChoiceDialogData
            selected = data.items.indexOfFirst(Item::isChecked)
        }

        activity?.let {
            val builder = AlertDialog.Builder(it)

            with(data) {

                val adapter = EquipmentOptionsAdapter(data.items, it).apply {
                    listener = { index ->
                        if (selected >= 0) data[selected].isChecked = false
                        data[index].isChecked = true
                        selected = index
                        notifyDataSetChanged()
                    }
                }

                val positiveButtonListener = DialogInterface.OnClickListener {
                    dialog, _ -> if (selected >= 0) onConfirm(selected) else onDismiss?.invoke()
                    dialog.dismiss()
                }

                builder.setTitle(title)
                        .setAdapter(adapter, null)
                        .setPositiveButton(R.string.ok, positiveButtonListener)

            }

            return builder.create()
        } ?: return super.onCreateDialog(savedInstanceState)
    }

}

class Item(val title: String, val subtitle: String, var isChecked: Boolean = false)

class SingleChoiceDialogData(val title: String,
                             val items: ArrayList<Item>,
                             var onDismiss: (() -> Unit)? = null,
                             val onConfirm: (index: Int) -> Unit): Serializable