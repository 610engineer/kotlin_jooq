package com.example.api

import com.example.api.model.Book
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApiService(private val apiRepository: ApiRepository){
    @Transactional(readOnly = true)
    fun getAllBooks(): String {
        return apiRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun getById(id: Int): String {
        return apiRepository.findById(id)
    }

    @Transactional(readOnly = true)
    fun getByAuthor(author: String): String {
        return apiRepository.findByAuthor(author)
    }

    @Transactional
    fun createBook(book:Book): String {
        return apiRepository.createBook(book)
    }

    @Transactional
    fun updateBook(id: Int, book:Book): String {
        return apiRepository.updateBook(id, book)
    }
}