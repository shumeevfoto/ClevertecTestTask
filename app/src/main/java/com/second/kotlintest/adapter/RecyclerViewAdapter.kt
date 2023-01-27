package com.second.kotlintest.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.second.kotlintest.R
import com.second.kotlintest.model.Field
import com.second.kotlintest.model.Type


class RecyclerViewAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private var inputData: MutableList<String> = mutableListOf()
    private var inputDataText: MutableList<String> = mutableListOf()
    private var inputDataNumber: MutableList<String> = mutableListOf()
    private var listData: List<Field>? = null
    private var mContext: Context? = context

    fun setUpdatedData(listData: List<Field>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    fun getData(): List<List<String>> {
        val text = inputDataText
        val value = inputDataNumber
        val checked = inputData
        return listOf(text, value, checked)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txt_title: TextView? = view.findViewById(com.second.kotlintest.R.id.txt_name)
        val editText: TextView? = view.findViewById(com.second.kotlintest.R.id.editText)
        val editTextNumber: TextView? = view.findViewById(com.second.kotlintest.R.id.editTextNumber)
        val mySpinner: Spinner? = view.findViewById(com.second.kotlintest.R.id.spinner)
//        private val value1: RadioButton? = view.findViewById(com.second.kotlintest.R.id.value1)
//        private val value2: RadioButton? = view.findViewById(com.second.kotlintest.R.id.value2)
//        private val value3: RadioButton? = view.findViewById(com.second.kotlintest.R.id.value3)
//        private val value4: RadioButton? = view.findViewById(com.second.kotlintest.R.id.value4)

        fun bind(data: Field) {
            txt_title?.text = data.title
            val arr:List<String> = listOf("Test1", "Test2")
            val value = data.values?.values
            Toast.makeText(mContext, value.toString(), Toast.LENGTH_SHORT).show()
            val dataAdapter = ArrayAdapter(mContext!!,
                android.R.layout.simple_spinner_item, arr)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mySpinner?.adapter = dataAdapter
            val a = mySpinner?.selectedItem.toString()
            inputData = listOf(a) as MutableList<String>
//            value1?.text = data.values.none
//            value2?.text = data.values.v1
//            value3?.text = data.values.v2
//            value4?.text = data.values.v3


//            val mListPhone = data.values
//             val arr = listOf("A", "B")
//        val adapter= context?.let {
//            ArrayAdapter(it, android.R.layout.simple_spinner_item,
//                arr)
//        }
//        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        myListView?.adapter = adapter
//            myListView?.setOnItemClickListener { _, _, position, _ ->
////                val selectContact = it[position].phone
//
//            }

//            val listView: ListView = findViewById(R.id.listView) as ListView
//
//            val arrayList: ArrayList<HashMap<String, String>> = ArrayList()
//            var map: HashMap<String?, String?>
//            val adapter = SimpleAdapter(this,
//                arrayList,
//                android.R.layout.simple_list_item_1,
//                arrayOf("Name"),
//                intArrayOf(android.R.id.text1))
//            listView.setAdapter(adapter)

//            value1?.setOnCheckedChangeListener { buttonView, isChecked ->
//                var value1 = ""
//                if (isChecked) value1 = "none"
//                inputData.clear()
//                inputData.add(value1)
//            }
//
//            value2?.setOnCheckedChangeListener { buttonView, isChecked ->
//                var value2 = ""
//                if (isChecked) value2 = "v1"
//                inputData.clear()
//                inputData.add(value2)
//
//            }
//
//            value3?.setOnCheckedChangeListener { buttonView, isChecked ->
//                var value3 = ""
//                if (isChecked) value3 = "v2"
//                inputData.clear()
//                inputData.add(value3)
//
//            }
//
//            value4?.setOnCheckedChangeListener { buttonView, isChecked ->
//                var value4 = ""
//                if (isChecked) value4 = "v3"
//                inputData.clear()
//                inputData.add(value4)
//
//            }
        }
    }

//    private fun ArrayAdapter(myViewHolder: RecyclerViewAdapter.MyViewHolder, simpleListItem1: Int, arr: List<String>): SpinnerAdapter? {
//
//    }

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



//        Toast.makeText(mContext, a, Toast.LENGTH_SHORT).show()

//        holder.mySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                itemSelected: View, selectedItemPosition: Int, selectedId: Long,
//            ) {
//                inputData = listOf(a) as MutableList<String>
//            }
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//        }


//        }
//        val arr = listOf("A", "B")
//        val adapter= context?.let {
//            ArrayAdapter(it, android.R.layout.simple_spinner_item,
//                arr)
//        }
//        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        holder.myListView?.adapter = adapter
//        holder.myListView?.setOnItemClickListener { _, _, position, _ ->
//                val selectContact = it[position].phone
//        }

        holder.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(txt: Editable?) {
                val text = txt.toString()
                inputDataText.clear()
                inputDataText.add(text)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        holder.editTextNumber?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(num: Editable?) {
                val number = num.toString()
                inputDataNumber.clear()
                inputDataNumber.add(number)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}