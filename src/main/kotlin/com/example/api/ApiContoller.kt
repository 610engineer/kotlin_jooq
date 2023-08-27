package com.example.api


import org.apache.commons.logging.LogFactory
import org.jooq.Record
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class ApiController( private val apiService: ApiService) {
    private val log = LogFactory.getLog(ApiController::class.java)
    @GetMapping("/all")
    fun getAllBooks(): List<Record> {
        log.info("PASSED")
        return apiService.getAllBooks()
    }

    @GetMapping("/hello")
    fun helloWorld(): String {
        return "Hello World"
    }
}