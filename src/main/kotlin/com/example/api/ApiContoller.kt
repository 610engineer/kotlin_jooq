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

    @GetMapping("/search")
    fun getBookByAuthor(@RequestParam author: String): Book?{
        return apiService.getByAuthor(author)
    }

    @PostMapping
    fun createBook(@RequestBody book: Book) :Book{
        return apiService.createBook(book)
    }

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: Int,@RequestBody book: Book) : Book?{
        return apiService.updateBook(id ,book)
    }


    @GetMapping("/hello")
    fun helloWorld(): String {
        return "Hello World"
    }
}