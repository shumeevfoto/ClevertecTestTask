package com.second.kotlintest.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.second.kotlintest.R
import com.second.kotlintest.model.Field
import com.second.kotlintest.model.Type


class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private var inputData: MutableList<String> = mutableListOf("", "", "")
    private var listData: List<Field>? = null

    fun setUpdatedData(listData: List<Field>) {
        this.listData = listData
        notifyDataSetChanged()
    }


    fun getData():List<String> {
        val text = inputData[0]
        val value = inputData[1]
        val checked = inputData[2]
        return listOf(text, value, checked)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txt_title: TextView = view.findViewById(R.id.txt_name)
        val editText: TextView? = view.findViewById(R.id.editText)
        val editTextNumber: TextView? = view.findViewById(R.id.editTextNumber)
        val value1: RadioButton? = view.findViewById(R.id.value1)
        val value2: RadioButton? = view.findViewById(R.id.value2)
        val value3: RadioButton? = view.findViewById(R.id.value3)
        val value4: RadioButton? = view.findViewById(R.id.value4)

        fun bind(data: Field) {
            txt_title.text = data.title
            value1?.text = data.values.none
            value2?.text = data.values.v1
            value3?.text = data.values.v2
            value4?.text = data.values.v3

            value1?.setOnCheckedChangeListener { buttonView, isChecked ->
                var value1 = inputData[2]
                if (isChecked) value1 = "none"
                inputData.removeAt(2)
                inputData.add(2, value1)
            }

            value2?.setOnCheckedChangeListener { buttonView, isChecked ->
                var value2 = inputData[2]
                if (isChecked) value2 = "v1"
                inputData.removeAt(2)
                inputData.add(2, value2)

            }

            value3?.setOnCheckedChangeListener { buttonView, isChecked ->
                var value3 = inputData[2]
                if (isChecked) value3 = "v2"
                inputData.removeAt(2)
                inputData.add(2, value3)

            }

            value4?.setOnCheckedChangeListener { buttonView, isChecked ->
                var value4 = inputData[2]
                if (isChecked) value4 = "v3"
                inputData.removeAt(2)
                inputData.add(2, value4)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = when (viewType) {
            1 -> R.layout.recycler_view_list_row_text
            2 -> R.layout.recycler_view_list_row_number
            3 -> R.layout.recycler_view_list_row_value
            else -> throw java.lang.RuntimeException("Unknown view type:$viewType")
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {

        return when (listData?.get(position)?.type) {
            Type.TEXT.name -> 1
            Type.NUMERIC.name -> 2
            Type.LIST.name -> 3
            else -> 0
        }
    }

    override fun getItemCount(): Int {
        return if (listData == null) 0
        else listData?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listData?.get(position)!!)

        holder.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(txt: Editable?) {
                val text = txt.toString()
                inputData.removeAt(0)
                inputData.add(0, text)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        holder.editTextNumber?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(num: Editable?) {
                val number = num.toString()
                inputData.removeAt(1)
                inputData.add(1, number)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}