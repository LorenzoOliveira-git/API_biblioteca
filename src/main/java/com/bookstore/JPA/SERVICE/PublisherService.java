package com.bookstore.JPA.SERVICE;

import com.bookstore.JPA.DTOs.Book.ResponseBookRecord;
import com.bookstore.JPA.DTOs.Publisher.PublisherRecord;
import com.bookstore.JPA.DTOs.Publisher.ResponseAllPublisherRecord;
import com.bookstore.JPA.DTOs.Publisher.ResponsePublisherRecord;
import com.bookstore.JPA.DTOs.UUIDsRecord;
import com.bookstore.JPA.MODELs.Book;
import com.bookstore.JPA.MODELs.Publisher;
import com.bookstore.JPA.REPOSITORIES.BookRepository;
import com.bookstore.JPA.REPOSITORIES.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final BookRepository bookRepository;
    PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository, BookRepository bookRepository){
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public ResponseAllPublisherRecord savePublisher(PublisherRecord publisherRecord)throws ResponseStatusException {
        var publisher = new Publisher();
        publisher.setName(publisherRecord.name());
        List<UUID> booksIds = publisherRecord.booksIds();
        if(!booksIds.isEmpty()){
            Set<Book> books = new HashSet<>();
            for(UUID id: booksIds){
                Optional<Book> bookO = bookRepository.findById(id);
                if(bookO.isEmpty()){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Book with ID = "+id+". Not found");
                }
                books.add(bookO.get());
            }
            publisher.setBooks(books);
        }
        publisherRepository.save(publisher);
        return new ResponseAllPublisherRecord(publisher.getId(),
                publisher.getName(),
                publisher.getBooks().
                        stream().
                        map(b -> new ResponseBookRecord(b.getId(),b.getTitle(),b.getReview())).
                        collect(Collectors.toList()));
    }

    @Transactional
    public ResponseAllPublisherRecord saveBooksOfPublisher(UUID id,
                                                         UUIDsRecord booksIds) throws ResponseStatusException{
        Optional<Publisher> publisherO = publisherRepository.findById(id);
        List<UUID> ids = booksIds.listUUIDs();
        if(!ids.isEmpty()){
            Set<Book> books = new HashSet<>();
            for(UUID idBook: ids){
                Optional<Book> bookO = bookRepository.findById(idBook);
                if(bookO.isEmpty()){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Book with ID = "+idBook+". Not found");
                }
                books.add(bookO.get());
            }
            publisherO.get().setBooks(books);
        }
        return new ResponseAllPublisherRecord(publisherO.get().getId(),
                publisherO.get().getName(),
                publisherO.get().getBooks().
                        stream().
                        map(b -> new ResponseBookRecord(b.getId(),b.getTitle(),b.getReview())).
                        collect(Collectors.toList()));
    }

    public List<ResponseBookRecord> listBooksByPublisher(UUID id) throws ResponseStatusException{
        Optional<Publisher> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Publisher not found.");
        }
        List<ResponseBookRecord> books =
                publisherO.get().getBooks().
                        stream().
                        map(b -> new ResponseBookRecord(b.getId(),b.getTitle(),b.getReview())).
                        collect(Collectors.toList());
        if(books.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Publisher don't have books yet.");
        }
        return books;
    }


    public List<ResponseAllPublisherRecord> listPublishersWithBooks() throws ResponseStatusException{
        List<Publisher> publishers = publisherRepository.findAll();
        if(publishers.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There " +
                    "aren't publishers in system yet.");
        }
        return publishers.
                stream().
                map(p -> new ResponseAllPublisherRecord(p.getId(),
                        p.getName(),
                        p.getBooks().
                                stream().
                                map(b -> new ResponseBookRecord(b.getId(),b.getTitle(),b.getReview())).
                                collect(Collectors.toList()))).
                collect(Collectors.toList());
    }

    public List<ResponsePublisherRecord> listPublishers() throws ResponseStatusException{
        List<Publisher> publishers = publisherRepository.findAll();
        if(publishers.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There " +
                    "aren't publishers in the system yet.");
        }
        return publishers.
                stream().
                map(p -> new ResponsePublisherRecord(p.getId(),
                        p.getName())).
                collect(Collectors.toList());
    }

    public ResponsePublisherRecord getPublisher(UUID id) throws ResponseStatusException{
        Optional<Publisher> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not " +
                    "found.");
        }
        return new ResponsePublisherRecord(publisherO.get().getId(),
                publisherO.get().getName());
    }

    public ResponseAllPublisherRecord getPublisherWithBooks(UUID id) throws ResponseStatusException{
        Optional<Publisher> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher" +
                    " not found");
        }
        return new ResponseAllPublisherRecord(publisherO.get().getId(),
                publisherO.get().getName(),
                publisherO.get().getBooks().
                        stream().
                        map(b -> new ResponseBookRecord(b.getId(),b.getTitle(),b.getReview())).
                        collect(Collectors.toList()));
    }

    @Transactional
    public String deletePublisher(UUID id) throws ResponseStatusException{
        Optional<Publisher> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not " +
                    "found");
        }
        publisherRepository.delete(publisherO.get());
        return "Delete publisher was doing with success.";
    }

    @Transactional
    public String deletePublishers(UUIDsRecord publishersIds){
        for(UUID id: publishersIds.listUUIDs()){
            Optional<Publisher> publisherO = publisherRepository.findById(id);
            if(publisherO.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Publisher with ID = "+id+". Not found.");
            }
            publisherRepository.delete(publisherO.get());
        }
        return "The process of delete publishers was success.";
    }

    @Transactional
    public ResponseAllPublisherRecord updatePublisher(UUID id,
                                                      PublisherRecord publisherRecord) throws ResponseStatusException{
        Optional<Publisher> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not " +
                    "found.");
        }
        publisherO.get().setName(publisherRecord.name());
        List<UUID> bookIds = publisherRecord.booksIds();
        if(!bookIds.isEmpty()){
            Set<Book> books = new HashSet<>();
            for(UUID idBook: bookIds){
                Optional<Book> bookO = bookRepository.findById(idBook);
                if(bookO.isEmpty()){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Book with ID = "+idBook+". Not found.");
                }
                books.add(bookO.get());
            }
            publisherO.get().setBooks(books);
        }
        publisherRepository.save(publisherO.get());
        return new ResponseAllPublisherRecord(publisherO.get().getId(),
                publisherO.get().getName(),
                publisherO.get().getBooks().
                        stream().
                        map(b -> new ResponseBookRecord(b.getId(),b.getTitle(),b.getReview())).
                        collect(Collectors.toList()));
    }

}
