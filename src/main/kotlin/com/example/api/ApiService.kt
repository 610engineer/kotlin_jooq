package com.example.api

import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Service

@Service
class ApiService(private val apiRepository: ApiRepository){
    fun getAllBooks(): List<Record> {
        return apiRepository.findAll()
    }
}