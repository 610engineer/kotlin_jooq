package com.example.api

import com.example.api.model.Book
import org.springframework.stereotype.Service

@Service
class ApiService(private val apiRepository: ApiRepository){
    fun getAllBooks(): String {
        return apiRepository.findAll()
    }

    fun getById(id: Int): String {
        return apiRepository.findById(id)
    }

    fun getByAuthor(author: String): String {
        return apiRepository.findByAuthor(author)
    }

    fun createBook(book:Book): String {
        return apiRepository.createBook(book)
    }

    fun updateBook(id: Int, book:Book): String {
        return apiRepository.updateBook(id, book)
    }

}