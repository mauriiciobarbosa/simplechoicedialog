package com.mauricio.singlechoicedialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mauricio.simplechoicedialog.Item
import com.mauricio.simplechoicedialog.SingleChoiceDialog
import com.mauricio.simplechoicedialog.SingleChoiceDialogData
import kotlinx.android.synthetic.main.actv_main.*

class MainActivity : AppCompatActivity() {

    val items = arrayListOf(
        Item("Item 1", "This is the item 1", true),
        Item("Item 2", "This is the item 2", false),
        Item("Item 3", "This is the item 3", false),
        Item("Item 4", "This is the item 4", false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actv_main)

        button.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog() {
        val data = SingleChoiceDialogData("My dialog", items) { indexOfSelectedItem ->
            items.forEach { item -> item.isChecked = false }
            items[indexOfSelectedItem].isChecked = true
            textview.text = items[indexOfSelectedItem].title
        }

        SingleChoiceDialog
            .newInstance(data)
            .show(supportFragmentManager, null)
    }
}
