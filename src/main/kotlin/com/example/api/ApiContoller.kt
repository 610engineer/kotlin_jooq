package com.example.api


import com.example.api.model.Book
import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books")
class ApiController( private val apiService: ApiService) {
    private val log = LogFactory.getLog(ApiController::class.java)
    @GetMapping("")
    fun getAllBooks(): String {
        return apiService.getAllBooks()
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Int):String{
        return apiService.getById(id)
    }

    @GetMapping("/search")
    fun getBookByAuthor(@RequestParam author: String): String{
        return apiService.getByAuthor(author)
    }

    @PostMapping
    fun createBook(@RequestBody book: Book) :String{
        return apiService.createBook(book)
    }

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: Int,@RequestBody book: Book) : String{
        return apiService.updateBook(id ,book)
    }

}