package com.bookstore.JPA.CONTROLLER;

import com.bookstore.JPA.DTOs.Author.AuthorRecord;
import com.bookstore.JPA.SERVICE.AuthorService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/bookstore/authors")
public class AuthorController {

    AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Object> saveAuthor(@RequestBody @Valid AuthorRecord authorRecord){
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.saveAuthor(authorRecord));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAuthor(@PathVariable(name = "id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(authorService.listOneAuthor(id));
    }
}
