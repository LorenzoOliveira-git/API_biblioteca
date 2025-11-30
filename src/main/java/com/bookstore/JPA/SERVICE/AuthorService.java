package com.bookstore.JPA.SERVICE;

import com.bookstore.JPA.DTOs.Author.AuthorRecord;
import com.bookstore.JPA.DTOs.Author.ResponseAllAuthorRecord;
import com.bookstore.JPA.DTOs.Author.ResponseAuthorRecord;
import com.bookstore.JPA.DTOs.Author.UUIDsRecord;
import com.bookstore.JPA.DTOs.Book.BookSummaryRecord;
import com.bookstore.JPA.MODELs.Author;
import com.bookstore.JPA.MODELs.Book;
import com.bookstore.JPA.REPOSITORIES.AuthorRepository;
import com.bookstore.JPA.REPOSITORIES.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorService{

    private final BookRepository bookRepository;
    AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository){
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public ResponseAuthorRecord saveAuthor(AuthorRecord authorRecord) throws ResponseStatusException{
        var author = new Author();
        BeanUtils.copyProperties(authorRecord,author);
        if(authorRepository.findByName(author.getName()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This " +
                    "author already was registre.");
        }
        authorRepository.save(author);
        return new ResponseAuthorRecord(author.getId(),author.getName());
    }

    @Transactional
    public ResponseAllAuthorRecord saveBooksOfAuthor(UUID id,
                                                     UUIDsRecord uuidsRecord)throws ResponseStatusException {
        if(uuidsRecord.listUUIDs().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You " +
                    "brought a author without values.");
        }
        Optional<Author> author = authorRepository.findById(id);
        if(author.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author " +
                    "not found.");
        }
        Set<Book> books =
                bookRepository.findAllById(uuidsRecord.listUUIDs()).stream().collect(Collectors.toSet());
        BeanUtils.copyProperties(books,author);
        authorRepository.save(author.get());
        return new ResponseAllAuthorRecord(author.get().getId(),
                author.get().getName(),
                author.get()
                        .getBooks()
                        .stream()
                        .map(b -> new BookSummaryRecord(b.getId(),b.getTitle(),b.getReview()))
                        .collect(Collectors.toSet()));
    }

    public List<ResponseAuthorRecord> listAllAuthors() throws ResponseStatusException{
        List<ResponseAuthorRecord> authors = authorRepository
                .findAll()
                .stream()
                .map(a -> new ResponseAuthorRecord(a.getId(),a.getName()))
                .collect(Collectors.toList());
        if(authors.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author " +
                    "not found.");
        }
        return authors;
    }

    public List<ResponseAllAuthorRecord> listAllAuthorsWithBooks() throws ResponseStatusException{
        List<ResponseAllAuthorRecord> authorBooksRecords =
                authorRepository.
                        findAll().
                        stream().
                        map(a -> new ResponseAllAuthorRecord(a.getId(),
                                a.getName(),
                                a.getBooks()
                                        .stream()
                                        .map(b -> new BookSummaryRecord(b.getId(),b.getTitle(),b.getReview()))
                                        .collect(Collectors.toSet())))
                        .collect(Collectors.toList());
        if(authorBooksRecords.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"There " +
                    "aren't authors in the system yet.");
        }
        return authorBooksRecords;
    }

    public ResponseAllAuthorRecord listOneAuthorWithBooks(UUID id) throws ResponseStatusException{
        var author = authorRepository.findByIdWithBooks(id);
        if(author == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author " +
                    "not found");
        }
        Set<BookSummaryRecord> books = author.getBooks().stream().
                map(b -> new BookSummaryRecord(b.getId(),b.getTitle(),
                        b.getReview())).collect(Collectors.toSet());
        return new ResponseAllAuthorRecord(author.getId(),author.getName(),
                books);
    }

    public ResponseAuthorRecord listOneAuthor(UUID id) throws ResponseStatusException{
        Optional<Author> authorO =
                authorRepository.findById(id);
        if(authorO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author " +
                    "not found.");
        }
        return new ResponseAuthorRecord(authorO.get().getId(),
                authorO.get().getName());
    }

    public Set<BookSummaryRecord> listBooksByAuthor(UUID id) throws ResponseStatusException{
        Optional<Author> authorO = authorRepository.findById(id);
        if(authorO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author " +
                    "not found.");
        }
        Set<BookSummaryRecord> books =
                authorO.get()
                        .getBooks()
                        .stream()
                        .map(b -> new BookSummaryRecord(b.getId(),b.getTitle(),b.getReview()))
                        .collect(Collectors.toSet());
        if(books.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author " +
                    "don't have books yet.");
        }
        return books;
    }

    @Transactional
    public String deleteAuthor(UUID id) throws ResponseStatusException{
        Optional<Author> authorO = authorRepository.findById(id);
        if(authorO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author " +
                    "not found.");
        }
        for (Book book : authorO.get().getBooks()) {
            book.getAuthors().remove(authorO.get());
        }
        authorO.get().getBooks().clear();
        authorRepository.delete(authorO.get());
        return "Delete author was doing with success.";
    }

    @Transactional
    public String deleteAuthors(UUIDsRecord uuidsRecord) throws ResponseStatusException{
        for (UUID id : uuidsRecord.listUUIDs()) {
            Optional<Author> authorO = authorRepository.findById(id);
            if (authorO.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Author with ID = "+id+". Not found.");
            }
            for (Book book : authorO.get().getBooks()) {
                book.getAuthors().remove(authorO.get());
            }
            authorO.get().getBooks().clear();
            authorRepository.delete(authorO.get());
        }
        return "The process of delete authors was success.";
    }

    @Transactional
    public ResponseAllAuthorRecord updateAuthor(UUID id,
                                              AuthorRecord authorRecord) throws ResponseStatusException{
        Optional<Author> authorO = authorRepository.findById(id);
        if(authorO.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Author " +
                    "not found.");
        }
        Author author = authorO.get();
        BeanUtils.copyProperties(authorRecord,author);
        authorRepository.save(author);
        return new ResponseAllAuthorRecord(id,author.getName(),
                author.getBooks().
                        stream().
                        map(b -> new BookSummaryRecord(b.getId(),b.getTitle(),b.getReview())).
                        collect(Collectors.toSet()));
    }



}
