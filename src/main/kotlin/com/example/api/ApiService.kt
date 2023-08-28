package com.example.api

import com.example.api.model.Book
import org.springframework.stereotype.Service

@Service
class ApiService(private val apiRepository: ApiRepository){
    fun getAllBooks(): MutableList<Book> {
        return apiRepository.findAll()
    }

    fun getById(id: Int): Book? {
        return apiRepository.findById(id)
    }

    fun insertItem(title: String, author: String): Book {
        return apiRepository.insert(title, author)
    }

    fun deleteTable(){
        apiRepository.deleteAll()
    }
}