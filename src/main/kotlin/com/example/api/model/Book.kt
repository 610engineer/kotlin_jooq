package com.example.api.model

import com.example.ktknowledgeTodo.infra.jooq.tables.records.BooksRecord

data class Book(val id: Int, val title: String, val author: String)