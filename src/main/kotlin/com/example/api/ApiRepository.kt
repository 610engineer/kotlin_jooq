package com.example.api

import com.example.api.model.Book
import com.example.ktknowledgeTodo.infra.jooq.tables.Books
import com.example.ktknowledgeTodo.infra.jooq.tables.references.BOOKS
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository

@Repository
class ApiRepository(private val dslContext: DSLContext){
    fun findAll(): MutableList<Book> {

        return this.dslContext.select()
            .from(BOOKS)
            .fetch().map { toModel(it) }
    }

    fun findById(id: Int): Book? {
        return dslContext.select()
            .from(BOOKS)
            .where(BOOKS.ID.eq(id))
            .fetchOne()?.let { toModel(it) }
    }

    fun insert(title: String, author: String): Book {
        val record = dslContext.newRecord(BOOKS).also{
            it.title = title
            it.author = author
            it.store()
        }
        return Book(record.id!! , record.title!!, record.author!!)
    }

    fun deleteAll(){
        dslContext.deleteFrom(BOOKS).execute()
    }

    private fun toModel(record: Record) = Book(
            record.getValue(BOOKS.ID)!!,
            record.getValue(BOOKS.TITLE)!!,
            record.getValue(BOOKS.AUTHOR)!!
    )

}