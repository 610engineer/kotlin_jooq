package com.example.api

import com.example.api.model.Book
import com.example.ktknowledgeTodo.infra.jooq.tables.references.BOOKS
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository

@Repository
class ApiRepository(private val dslContext: DSLContext){
    fun findAll(): MutableList<Book> {

        return this.dslContext.select().from(BOOKS).fetch().map { toModel(it) }
    }

    private fun toModel(record: Record) = Book(
            record.getValue(BOOKS.ID)!!,
            record.getValue(BOOKS.TITLE)!!,
            record.getValue(BOOKS.AUTHOR)!!
    )

}