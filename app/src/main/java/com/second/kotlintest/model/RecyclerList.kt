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
    val values: Values
)

data class Values(
    val none: String = "не выбрано",
    val v1: String,
    val v2: String,
    val v3: String
)

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
