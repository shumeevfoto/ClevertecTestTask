package com.second.kotlintest.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.text.Editable
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.second.kotlintest.R
import com.second.kotlintest.model.Field
import com.second.kotlintest.model.Input
import com.second.kotlintest.model.Type

class RecyclerViewAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private var inputValue: MutableList<String> = mutableListOf()
    private var inputDataText: MutableList<String> = mutableListOf()
    private var inputDataNumber: MutableList<Double> = mutableListOf()
    private var listData: List<Field>? = null
    private var mContext: Context? = context

    fun setUpdatedData(listData: List<Field>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    fun getData(): Input {
        val text = inputDataText
        val number = inputDataNumber
        val checked = inputValue
        return Input(text, number, checked)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txt_title: TextView? = view.findViewById(R.id.txt_name)
        val editText: TextView? = view.findViewById(com.second.kotlintest.R.id.editText)
        val editTextNumber: TextView? = view.findViewById(com.second.kotlintest.R.id.editTextNumber)
        val mySpinner: Spinner? = view.findViewById(R.id.spinner)

        fun bind(data: Field) {

            txt_title?.text = data.title
            val value = data.values?.values?.toList()
            Log.d(TAG, "bind: $value")
            if (value != null) {
                val dataAdapter = ArrayAdapter(mContext!!,
                    android.R.layout.simple_spinner_item, value)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                mySpinner?.adapter = dataAdapter
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

        holder.mySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapter: AdapterView<*>, v: View?, i: Int, lng: Long) {
                val selectedItem = adapter.getItemAtPosition(i).toString()
                inputValue.clear()
                inputValue.add(selectedItem)
                Log.d(TAG, "onBindViewHolder: $selectedItem")
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        holder.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(txt: Editable?) {
                val text = txt.toString()
                inputDataText.clear()
                inputDataText.add(text)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        val inputType = TYPE_CLASS_NUMBER or TYPE_NUMBER_FLAG_DECIMAL
        holder.editTextNumber?.inputType = inputType
        holder.editTextNumber?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(num: Editable?) {
                inputDataNumber.clear()
                inputDataNumber.add(num.toString().toDouble())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}