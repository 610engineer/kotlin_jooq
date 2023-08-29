package com.example.api

import com.example.api.model.Book
import com.example.ktknowledgeTodo.infra.jooq.tables.records.BooksRecord
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
    fun findByAuthor(author: String):Book?{
        return dslContext.select()
            .from(BOOKS)
            .where(BOOKS.AUTHOR.eq(author))
            .fetchOne()?.let { toModel(it) }
    }

    fun createBook(book:Book): Book {
        val record = dslContext.newRecord(BOOKS).also{
            it.title = book.title
            it.author = book.author
            it.store()
        }
        return Book(record.id!! , record.title!!, record.author!!)
    }

    fun updateBook(id: Int, book:Book):Book?{
        val record = dslContext.update(BOOKS)
            .set(BOOKS.TITLE, book.title)
            .set(BOOKS.AUTHOR, book.author)
            .where(BOOKS.ID.eq(id))
            .execute()
        return if (record > 0) {
            findById(id)
        } else{
            null
        }
    }

    private fun toModel(record: Record) = Book(
            record.getValue(BOOKS.ID)!!,
            record.getValue(BOOKS.TITLE)!!,
            record.getValue(BOOKS.AUTHOR)!!
    )

}