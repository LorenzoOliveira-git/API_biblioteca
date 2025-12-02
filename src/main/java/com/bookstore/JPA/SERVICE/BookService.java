package com.bookstore.JPA.SERVICE;

import com.bookstore.JPA.DTOs.Author.ResponseAuthorRecord;
import com.bookstore.JPA.DTOs.Book.BookRecord;
import com.bookstore.JPA.DTOs.Book.ResponseAllBookRecord;
import com.bookstore.JPA.DTOs.Book.ResponseBookRecord;
import com.bookstore.JPA.DTOs.Publisher.ResponsePublisherRecord;
import com.bookstore.JPA.DTOs.Review.ResponseReviewRecord;
import com.bookstore.JPA.DTOs.Review.ReviewRecord;
import com.bookstore.JPA.DTOs.UUIDRecord;
import com.bookstore.JPA.DTOs.UUIDsRecord;
import com.bookstore.JPA.MODELs.*;
import com.bookstore.JPA.REPOSITORIES.AuthorRepository;
import com.bookstore.JPA.REPOSITORIES.BookRepository;
import com.bookstore.JPA.REPOSITORIES.PublisherRepository;
import com.bookstore.JPA.REPOSITORIES.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    ReviewRepository reviewRepository;
    BookRepository bookRepository;
    AuthorRepository authorRepository;
    PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       PublisherRepository publisherRepository,
                       ReviewRepository reviewRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.reviewRepository = reviewRepository;
    }


    @Transactional // serve para implementar a função de Roolback caso haja
    // um erro no processo.
    public ResponseBookRecord saveBook(BookRecord bookRecord) throws ResponseStatusException {
        var book = new Book();
        book.setTitle(bookRecord.title());
        UUID idPublisher = bookRecord.publisher_id();
        if(idPublisher != null){
            Optional<Publisher> publisherO =
                    publisherRepository.findById(idPublisher);
            if(publisherO.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Publisher not found.");
            }
            book.setPublisher(publisherO.get());
        }
        Set<Author> authors = new HashSet<>();
        Set<UUID> idsAuthors = bookRecord.authorIds();
        if(!idsAuthors.isEmpty()) {
            for (UUID id : idsAuthors) {
                Optional<Author> authorO = authorRepository.findById(id);
                if (authorO.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Author with ID = "+id+". Not found.");
                }
                authors.add(authorO.get());
            }
            book.setAuthors(authors);
        }
        if(!bookRecord.reviewComment().isEmpty()) {
            Review review = new Review();
            review.setComment(bookRecord.reviewComment());
            review.setBook(book);
            book.setReview(review);
        }
        bookRepository.save(book);
        return new ResponseBookRecord(book.getId(),book.getTitle(),book.getReview());
    }
    @Transactional
    public ResponsePublisherRecord savePublisherByBook(UUID id,
                                                       UUIDRecord idPublisher) throws ResponseStatusException{
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not " +
                    "found.");
        }
        Optional<Publisher> publisher =
                publisherRepository.findById(idPublisher.id());
        if(publisher.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Publisher not found.");
        }
        book.get().setPublisher(publisher.get());
        bookRepository.save(book.get());
        return new ResponsePublisherRecord(publisher.get().getId(),
                publisher.get().getName());
    }

    @Transactional
    public List<ResponseAuthorRecord> saveAuthorsByBook(UUID id,
                                                  UUIDsRecord listUUIDs) throws ResponseStatusException{
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not " +
                    "found.");
        }
        Set<Author> authors = new HashSet<>();
        for(UUID idAuthor: listUUIDs.listUUIDs()){
            Optional<Author> authorO = authorRepository.findById(idAuthor);
            if(authorO.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author with ID = "+idAuthor+". Not found.");
            }
            authors.add(authorO.get());
        }
        book.get().setAuthors(authors);
        bookRepository.save(book.get());
        return authors.stream().map(a -> new ResponseAuthorRecord(a.getId(),
                a.getName())).collect(Collectors.toList());
    }

    public List<ResponseAllBookRecord> listBooksWithAuthorsAndPublisher() throws ResponseStatusException{
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"There " +
                    "aren't books in the system yet.");
        }
        return books.stream().map(b -> new ResponseAllBookRecord(
                b.getId(),
                b.getTitle(),
                new ResponsePublisherRecord(b.getPublisher().getId(),
                        b.getPublisher().getName()),
                b.getAuthors()
                        .stream()
                        .map(a -> new ResponseAuthorRecord(a.getId(),a.getName()))
                        .collect(Collectors.toSet()),
                b.getReview()))
                .collect(Collectors.toList());
    }

    public List<ResponseBookRecord> listBooks() throws ResponseStatusException{
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There " +
                    "aren't books in the system yet.");
        }
        return books.stream().map(b -> new ResponseBookRecord(b.getId(),
                b.getTitle(),b.getReview())).collect(Collectors.toList());
    }

    public ResponseAllBookRecord getBookWithAuthorsAndPublisher(UUID id) throws ResponseStatusException{
        Optional<Book> bookO = bookRepository.findById(id);
        if(bookO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not" +
                    " found");
        }
        return new ResponseAllBookRecord(bookO.get().getId(),
                bookO.get().getTitle(),
                new ResponsePublisherRecord(bookO.get().getPublisher().getId(),
                        bookO.get().getPublisher().getName()),
                bookO.get().getAuthors()
                        .stream()
                        .map(a -> new ResponseAuthorRecord(a.getId(),a.getName()))
                        .collect(Collectors.toSet())
                ,bookO.get().getReview());
    }

    public ResponseBookRecord getBook(UUID id) throws ResponseStatusException{
        Optional<Book> bookO = bookRepository.findById(id);
        if(bookO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not" +
                    " found.");
        }
        return new ResponseBookRecord(bookO.get().getId(),
                bookO.get().getTitle(),
                bookO.get().getReview());
    }

    public String deleteBook(UUID id) throws ResponseStatusException{
        Optional<Book> bookO = bookRepository.findById(id);
        if(bookO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not " +
                    "found.");
        }
        bookRepository.delete(bookO.get());
        return "The process of delete book was success.";
    }

    @Transactional
    public String deleteBooks(List<UUID> ids) throws ResponseStatusException{
        for(UUID id: ids){
            Optional<Book> bookO = bookRepository.findById(id);
            if(bookO.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Book " +
                        "with ID = "+id+". Not found");
            }
            bookRepository.delete(bookO.get());
        }
        return "The process of delete books was success.";
    }

    public ResponseAllBookRecord updateBook(UUID id, BookRecord bookRecord) throws ResponseStatusException{
        Optional<Book> bookO = bookRepository.findById(id);
        if(bookO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not" +
                    " found");
        }
        var book = bookO.get();
        BeanUtils.copyProperties(bookRecord,book);
        return new ResponseAllBookRecord(book.getId(),
                book.getTitle(),
                new ResponsePublisherRecord(
                        book.getPublisher().getId(),
                        book.getPublisher().getName()),
                book.getAuthors().
                        stream().
                        map(a -> new ResponseAuthorRecord(a.getId(),
                                a.getName()))
                        .collect(Collectors.toSet()),
                book.getReview()
                );
    }

//    Métodos para review
    @Transactional
    public ResponseReviewRecord saveReviewOfBook(UUID id,
                                                 ReviewRecord reviewRecord) throws ResponseStatusException{
        Optional<Book> bookO = bookRepository.findById(id);
        if(bookO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not " +
                    "found.");
        }
        var review = new Review();
        review.setComment(reviewRecord.comment());
        review.setBook(bookO.get());
        bookO.get().setReview(review);
        bookRepository.save(bookO.get());
        return new ResponseReviewRecord(review.getId(),review.getComment(),
                new ResponseBookRecord(review.getBook().getId(),
                        review.getBook().getTitle(),
                        review.getBook().getReview()));
    }

    public ResponseReviewRecord getReview(UUID id) throws ResponseStatusException{
        Optional<Book> bookO = bookRepository.findById(id);
        if(bookO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not " +
                    "found.");
        }
        var review = bookO.get().getReview();
        return new ResponseReviewRecord(review.getId(),review.getComment(),
                new ResponseBookRecord(review.getBook().getId(),
                        review.getBook().getTitle(),
                        review.getBook().getReview()));
    }
}
