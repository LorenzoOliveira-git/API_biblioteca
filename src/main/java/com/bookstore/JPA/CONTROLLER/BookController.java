package com.bookstore.JPA.CONTROLLER;

import com.bookstore.JPA.DTOs.Book.BookRecord;
import com.bookstore.JPA.DTOs.Review.ReviewRecord;
import com.bookstore.JPA.DTOs.UUIDRecord;
import com.bookstore.JPA.DTOs.UUIDsRecord;
import com.bookstore.JPA.REPOSITORIES.BookRepository;
import com.bookstore.JPA.SERVICE.BookService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;


@Controller
@RequestMapping("/bookstore/books")
public class BookController {

    BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Object> saveBook(@Valid BookRecord bookRecord){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookRecord));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @PostMapping("/saveAuthors/{id}")
    public ResponseEntity<Object> saveAuthorsOfBook(@PathVariable(name = "id")UUID id, @RequestBody UUIDsRecord listUUIDs){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveAuthorsByBook(id,listUUIDs));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @PostMapping("/savePublisher/{id}")
    public ResponseEntity<Object> savePublisherOfBook(@PathVariable(name =
            "id") UUID id, UUIDRecord idPublisher){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(bookService.savePublisherByBook(id,idPublisher));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @GetMapping("/allInfo=true")
    public ResponseEntity<Object> getBooksWithPublisherAndAuthors(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.listBooksWithAuthorsAndPublisher());
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @GetMapping("/allInfo=false")
    public ResponseEntity<Object> getBooks(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.listBooks());
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @GetMapping("/{id}/allInfo=true")
    public ResponseEntity<Object> getBookWithPublisherAndAuthors(@PathVariable(name = "id")UUID id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookWithAuthorsAndPublisher(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @GetMapping("/{id}/allInfo=false")
    public ResponseEntity<Object> getBook(@PathVariable(name = "id") UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.getBook(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(name = "id") UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.deleteBook(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBooks(@RequestBody UUIDsRecord listUUIDs){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.deleteBooks(listUUIDs));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putBook(@PathVariable(name = "id") UUID id,
                                          @RequestBody BookRecord bookRecord){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.updateBook(id,bookRecord));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @PostMapping("/review/{id}")
    public ResponseEntity<Object> saveReviewOfBook(@PathVariable(name = "id") UUID id, @RequestBody ReviewRecord reviewRecord){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.saveReviewOfBook(id,reviewRecord));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<Object> getReview(@PathVariable(name = "id") UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.getReview(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @GetMapping("/review")
    public ResponseEntity<Object> getReviews(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(bookService.getReviews());
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

}
