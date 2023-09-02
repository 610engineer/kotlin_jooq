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

        // booksテーブルに登録されているすべてのbookを取得
        val records = this.dslContext.select()
            .from(BOOKS)
            .fetch().map { toModel(it) }
        val objectMapper = jacksonObjectMapper();

        // recordがNULLでなければ取得したbookのリストを返す
        if (records != null){
            val result = ResultOKBooks(1, records)
            return objectMapper.writeValueAsString(result)
        }else{
            //登録がない場合はstatus:0を返す
            val result = ResultNG(0, "DATA_NOT_FOUND")
            return objectMapper.writeValueAsString(result)
        }
    }

    fun findById(id: Int): String {
        // 与えられたIDが一致するbookを取得
        val record = dslContext.select()
            .from(BOOKS)
            .where(BOOKS.ID.eq(id))
            .fetchOne()?.let { toModel(it) }

        val objectMapper = jacksonObjectMapper();

        //recordがNULLでなければIDが一致したbookを返す
        return if (record != null){
            val result = ResultOKBook(1, record)
            objectMapper.writeValueAsString(result)
        }else{
            // IDがテーブルに存在しなければstatus:0を返す
            val result = ResultNG(0, "DATA_NOT_FOUND")
            objectMapper.writeValueAsString(result)
        }
    }
    fun findByAuthor(author: String):String{
        // 与えられたauthorと一致するbookを取得
        val record = dslContext.select()
            .from(BOOKS)
            .where(BOOKS.AUTHOR.eq(author))
            .fetchOne()?.let { toModel(it) }

        val objectMapper = jacksonObjectMapper();

        // recordがNULLでない場合は一致したbookを返す
        return if (record != null){
            val result = ResultOKBook(1, record)
            objectMapper.writeValueAsString(result)
        }else{
            // テーブルに存在しないければstatus:0を返す
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

        // 与えられたIDのbookの内容をrequestBodyの内容に更新
        val record = dslContext.update(BOOKS)
            .set(BOOKS.TITLE, book.title)
            .set(BOOKS.AUTHOR, book.author)
            .where(BOOKS.ID.eq(id))
            .execute()

        // 更新が成功していた場合はiIDのbookを返す
        return if (record > 0) {
            findById(id)
        } else{
            //　更新に失敗していたらstatus:0を返す
            val objectMapper = jacksonObjectMapper();
            val result = ResultNG(0, "UPDATE_FAILED")
            return objectMapper.writeValueAsString(result)
        }
    }

    // recordの状態をBookのフォー的に変換
    private fun toModel(record: Record) = Book(
            record.getValue(BOOKS.ID)!!,
            record.getValue(BOOKS.TITLE)!!,
            record.getValue(BOOKS.AUTHOR)!!
    )

}