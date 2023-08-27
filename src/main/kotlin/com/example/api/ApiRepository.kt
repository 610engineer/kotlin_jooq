package com.example.api

import com.example.ktknowledgeTodo.infra.jooq.tables.references.BOOKS
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Repository

@Repository
class ApiRepository(private val dslContext: DSLContext){
    fun findAll(): List<Record> {

        return dslContext.select()
                .from(BOOKS)
                .fetch()
    }

}