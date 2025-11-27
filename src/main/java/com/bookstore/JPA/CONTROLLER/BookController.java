package com.bookstore.JPA.CONTROLLER;

import com.bookstore.JPA.DTOs.BookRecord;
import com.bookstore.JPA.MODELs.Book;
import com.bookstore.JPA.SERVICE.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/bookstore/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Object> saveBook(@RequestBody BookRecord bookRecord){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookRecord));
    }

}
