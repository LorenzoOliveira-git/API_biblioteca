package com.bookstore.JPA.SERVICE;

import com.bookstore.JPA.DTOs.Book.BookRecord;
import com.bookstore.JPA.DTOs.Book.BookSummaryRecord;
import com.bookstore.JPA.MODELs.*;
import com.bookstore.JPA.REPOSITORIES.AuthorRepository;
import com.bookstore.JPA.REPOSITORIES.BookRepository;
import com.bookstore.JPA.REPOSITORIES.BookSummaryRepository;
import com.bookstore.JPA.REPOSITORIES.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;
    PublisherRepository publisherRepository;
    BookSummaryRepository bookSummaryRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       PublisherRepository publisherRepository,
                       BookSummaryRepository bookSummaryRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.bookSummaryRepository = bookSummaryRepository;
    }


    @Transactional // serve para implementar a função de Roolback caso haja
    // um erro no processo.
    public Object saveBook(BookRecord bookRecord){
        Book book = new Book();
        try {
            book.setTitle(bookRecord.title());
            UUID idPublisher = bookRecord.publisher_id();
            if(idPublisher != null){
                Optional<Publisher> publisherO =
                        publisherRepository.findById(idPublisher);
                book.setPublisher(publisherO.get());
            }
            Set<Author> authors = new HashSet<>();
            Set<UUID> idsAuthors = bookRecord.authorIds();
            if(!idsAuthors.isEmpty()) {
                for (UUID id : idsAuthors) {
                    Optional<Author> authorO = authorRepository.findById(id);
                    if (authorO.isEmpty()) {
                        throw new Exception("Author with ID = " + id + ". Not found.");
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
        }catch(Exception e){
            return e.getMessage();
        }
        return bookRepository.save(book);
    }

    public Object getAllInfo(UUID id){
        Optional<Book> bookO = bookRepository.findById(id);
        if(bookO.isEmpty()){
            return "Book not found.";
        }
        return bookO.get();
    }

    public Object getAllInfo(){
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()){
            return "There aren't books in the system yet.";
        }
        return books;
    }

    public Object getBookSummary(UUID id){
        BookSummaryRecord book = bookSummaryRepository.findBookSummary(id);
        if(book == null){
            return "Book not found.";
        }
        return book;
    }

    public Object getBookSummary(){
        List<BookSummaryRecord> books = bookSummaryRepository.findBookSummary();
        if(books.isEmpty()){
            return "There aren't books in the system yet.";
        }
        return books;
    }

    public String deleteBook(UUID id){
        Optional<Book> bookO = bookRepository.findById(id);
        if(bookO.isEmpty()){
            return "Book not found.";
        }
        bookRepository.delete(bookO.get());
        return "The process of delete book was success.";
    }

    @Transactional
    public String deleteBooks(List<UUID> ids){
        try{
            for(UUID id: ids){
                Optional<Book> bookO = bookRepository.findById(id);
                if(bookO.isEmpty()){
                    throw new Exception("Book with ID = "+id+". Not found");
                }
                bookRepository.delete(bookO.get());
            }
        }catch(Exception e){
            return e.getMessage();
        }
        return "The process of delete books was success.";
    }

    public Object updateBook(UUID id, BookRecord bookRecord){
        Optional<Book> bookO = bookRepository.findById(id);
        if(bookO.isEmpty()){
            return "Book not found";
        }
        var book = bookO.get();
        BeanUtils.copyProperties(bookRecord,book);
        return book;
    }
}
