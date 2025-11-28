package com.bookstore.JPA.CONTROLLER;

import com.bookstore.JPA.DTOs.Book.BookRecord;
import com.bookstore.JPA.SERVICE.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Controller
@RequestMapping("/bookstore/books")
public class BookController {

    BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Object> saveBook(@RequestBody BookRecord bookRecord){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookRecord));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBook(@PathVariable(name = "id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllInfo(id));
    }

}
