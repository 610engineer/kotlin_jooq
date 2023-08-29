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

    fun getByAuthor(author: String): Book? {
        return apiRepository.findByAuthor(author)
    }

    fun createBook(book:Book): Book {
        return apiRepository.createBook(book)
    }

    fun updateBook(id: Int, book:Book): Book? {
        return apiRepository.updateBook(id, book)
    }

}