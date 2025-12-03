package com.bookstore.JPA.CONTROLLER;

import com.bookstore.JPA.DTOs.Publisher.PublisherRecord;
import com.bookstore.JPA.DTOs.UUIDsRecord;
import com.bookstore.JPA.SERVICE.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Controller
@RequestMapping("/bookstore/publisher")
public class PublisherController {

    PublisherService publisherService;

    public PublisherController(PublisherService publisherService){
        this.publisherService = publisherService;
    }

    @PostMapping
    public ResponseEntity<Object> savePublisher(PublisherRecord publisherRecord){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.savePublisher(publisherRecord));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> saveBooksOfPublisher(@PathVariable(name =
            "id")UUID id, UUIDsRecord listUUIDs){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.saveBooksOfPublisher(id,listUUIDs));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Object> getBooksByPublisher(@PathVariable(name =
            "id") UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(publisherService.listBooksByPublisher(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @GetMapping("/allInfo=true")
    public ResponseEntity<Object> getPublishersWithBooks(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(publisherService.listPublishersWithBooks());
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @GetMapping("/allInfo=false")
    public ResponseEntity<Object> getPublishers(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(publisherService.listPublishers());
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @GetMapping("/{id}/allInfo=true")
    public ResponseEntity<Object> getPublisherWithBooks(@PathVariable(name =
            "id") UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(publisherService.getPublisherWithBooks(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @GetMapping("/{id}/allInfo=false")
    public ResponseEntity<Object> getPublisher(@PathVariable(name =
            "id") UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(publisherService.getPublisher(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable(name = "id") UUID id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(publisherService.deletePublisher(id));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletePublishers(@RequestBody UUIDsRecord listUUIDs){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(publisherService.deletePublishers(listUUIDs));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putPublisher(@PathVariable(name = "id") UUID id,@RequestBody PublisherRecord publisherRecord){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(publisherService.updatePublisher(id, publisherRecord));
        }catch(ResponseStatusException rse){
            return ResponseEntity.status(rse.getStatusCode()).body(rse.getReason());
        }
    }
}
