package com.example.api


import com.example.api.model.Book
import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books")
class ApiController( private val apiService: ApiService) {
    private val log = LogFactory.getLog(ApiController::class.java)
    @GetMapping("")
    fun getAllBooks(): MutableList<Book> {
        return apiService.getAllBooks()
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Int):Book?{
        return apiService.getById(id)
    }

    @PostMapping
    fun createBook(@RequestBody title: String, @RequestBody author:String) :Book{
        return apiService.insertItem(title, author)
    }


    @GetMapping("/hello")
    fun helloWorld(): String {
        return "Hello World"
    }
}