package com.example.api

import com.example.api.mode.ResultOKBook
import com.example.api.mode.ResultOKBooks
import com.example.api.model.Book
import com.example.api.model.ResultNG
import com.example.ktknowledgeTodo.infra.jooq.tables.references.BOOKS
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jooq.DSLContext
import org.jooq.Record
import org.springframework.stereotype.Repository

@Repository
class ApiRepository(private val dslContext: DSLContext){
    fun findAll(): String {
        val records = this.dslContext.select()
            .from(BOOKS)
            .fetch().map { toModel(it) }
        val objectMapper = jacksonObjectMapper();
        if (records != null){
            val result = ResultOKBooks(1, records)
            return objectMapper.writeValueAsString(result)
        }else{
            val result = ResultNG(0, "DATA_NOT_FOUND")
            return objectMapper.writeValueAsString(result)
        }
    }

    fun findById(id: Int): String {
        val record = dslContext.select()
            .from(BOOKS)
            .where(BOOKS.ID.eq(id))
            .fetchOne()?.let { toModel(it) }

        val objectMapper = jacksonObjectMapper();
        return if (record != null){
            val result = ResultOKBook(1, record)
            objectMapper.writeValueAsString(result)
        }else{
            val result = ResultNG(0, "DATA_NOT_FOUND")
            objectMapper.writeValueAsString(result)
        }
    }
    fun findByAuthor(author: String):String{
        val record = dslContext.select()
            .from(BOOKS)
            .where(BOOKS.AUTHOR.eq(author))
            .fetchOne()?.let { toModel(it) }

        val objectMapper = jacksonObjectMapper();
        return if (record != null){
            val result = ResultOKBook(1, record)
            objectMapper.writeValueAsString(result)
        }else{
            val result = ResultNG(0, "DATA_NOT_FOUND")
            objectMapper.writeValueAsString(result)
        }
    }

    fun createBook(book:Book): String {
        val record = dslContext.newRecord(BOOKS).also{
            it.title = book.title
            it.author = book.author
            it.store()
        }
        val objectMapper = jacksonObjectMapper();
        return if (record != null){
            val result = ResultOKBook(1, Book(record.id!!, record.title!!, record.author!!))
            objectMapper.writeValueAsString(result)
        }else{
            val result = ResultNG(0, "CREATE_FAILED")
            objectMapper.writeValueAsString(result)
        }
    }

    fun updateBook(id: Int, book:Book):String{
        val record = dslContext.update(BOOKS)
            .set(BOOKS.TITLE, book.title)
            .set(BOOKS.AUTHOR, book.author)
            .where(BOOKS.ID.eq(id))
            .execute()
        return if (record > 0) {
            findById(id)
        } else{
            val objectMapper = jacksonObjectMapper();
            val result = ResultNG(0, "UPDATE_FAILED")
            return objectMapper.writeValueAsString(result)
        }
    }

    private fun toModel(record: Record) = Book(
            record.getValue(BOOKS.ID)!!,
            record.getValue(BOOKS.TITLE)!!,
            record.getValue(BOOKS.AUTHOR)!!
    )

}