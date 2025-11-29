package com.bookstore.JPA.CONTROLLER;

import com.bookstore.JPA.DTOs.Author.AuthorRecord;
import com.bookstore.JPA.DTOs.Author.AuthorUUIDsRecord;
import com.bookstore.JPA.SERVICE.AuthorService;
import jakarta.validation.Valid;
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

    @GetMapping("allInfo=false")
    public ResponseEntity<Object> getAuthors(){
        return ResponseEntity.status(HttpStatus.OK).body(authorService.listAllAuthors());
    }

    @GetMapping("allInfo=true")
    public ResponseEntity<Object> getAuthorsWithBooks(){
        return ResponseEntity.status(HttpStatus.OK).body(authorService.listAllAuthorsWithBooks());
    }

    @GetMapping("/{id}/allInfo=true")
    public ResponseEntity<Object> getAllInfoAuthor(@PathVariable(name = "id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(authorService.listOneAuthorWithBooks(id));
    }

    @GetMapping("/{id}/allInfo=false")
    public ResponseEntity<Object> getSomeInfoAuthor(@PathVariable(name = "id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(authorService.listOneAuthor(id));
    }


    @GetMapping("/books/{id}")
    public ResponseEntity<Object> getBooksByAuthor(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(authorService.listBooksByAuthor(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable(name = "id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(authorService.deleteAuthor(id));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAuthors(@RequestBody AuthorUUIDsRecord authorUUIDsRecord){
        return ResponseEntity.status(HttpStatus.OK).body(authorService.deleteAuthors(authorUUIDsRecord.listUUIDs()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable(name = "id") UUID id, @RequestBody AuthorRecord authorRecord){
        return ResponseEntity.status(HttpStatus.OK).body(authorService.updateAuthor(id,authorRecord));
    }
}
