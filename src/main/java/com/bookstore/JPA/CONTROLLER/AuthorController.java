package com.bookstore.JPA.CONTROLLER;

import com.bookstore.JPA.DTOs.Author.AuthorRecord;
import com.bookstore.JPA.DTOs.UUIDsRecord;
import com.bookstore.JPA.SERVICE.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authorService.saveAuthor(authorRecord));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getMessage());
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> saveBooksOfAuthor(@PathVariable(name = "id")UUID id,
                                                    @RequestBody UUIDsRecord uuidsRecord){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(authorService.saveBooksOfAuthor(id,uuidsRecord));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getMessage());
        }
    }

    @GetMapping("allInfo=false")
    public ResponseEntity<Object> getAuthors(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.listAuthors());
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getMessage());
        }
    }

    @GetMapping("allInfo=true")
    public ResponseEntity<Object> getAuthorsWithBooks(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.listAuthorsWithBooks());
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getMessage());
        }
    }

    @GetMapping("/{id}/allInfo=true")
    public ResponseEntity<Object> getAuthorWithBooks(@PathVariable(name = "id")UUID id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.getAuthorWithBooks(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getMessage());
        }
    }

    @GetMapping("/{id}/allInfo=false")
    public ResponseEntity<Object> getAuthor(@PathVariable(name = "id")UUID id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.getAuthor(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getMessage());
        }
    }


    @GetMapping("/books/{id}")
    public ResponseEntity<Object> getBooksByAuthor(@PathVariable(name = "id") UUID id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.listBooksByAuthor(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable(name = "id")UUID id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.deleteAuthor(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAuthors(@RequestBody UUIDsRecord uuidsRecord){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.deleteAuthors(uuidsRecord));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable(name = "id") UUID id, @RequestBody AuthorRecord authorRecord){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authorService.updateAuthor(id, authorRecord));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getMessage());
        }
    }
}
