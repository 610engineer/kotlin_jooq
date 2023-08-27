package com.example.api

import com.example.api.model.Book
import org.springframework.stereotype.Service

@Service
class ApiService(private val apiRepository: ApiRepository){
    fun getAllBooks(): MutableList<Book> {
        return apiRepository.findAll()
    }
}