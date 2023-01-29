package com.second.kotlintest.model

data class RecyclerList(
    val image: String,
    val title: String,
    val fields: List<Field>
)

data class Field(
    var title:String,
    val name: String,
    val type: String,
    var values: Map<String, String>
)
{
    fun setStringNumberMap(map: Map<String, String>) {
        values = map
    }
}

data class PostForm(
    val form: Form
) {
    data class Form(
        val text: String,
        val numeric: String,
        val list: String
    )
}

data class Result(
    val result: String
)

data class Input(
    val text: List<String>,
    val number: List<Double>,
    val checked: List<String>
)

