package com.bookstore.JPA.SERVICE;

import com.bookstore.JPA.DTOs.Author.AuthorBooksRecord;
import com.bookstore.JPA.DTOs.Author.AuthorRecord;
import com.bookstore.JPA.MODELs.Author;
import com.bookstore.JPA.MODELs.Book;
import com.bookstore.JPA.REPOSITORIES.AuthorRepository;
import com.bookstore.JPA.REPOSITORIES.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorService{

    AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Author saveAuthor(AuthorRecord authorRecord){
        var author = new Author();
        BeanUtils.copyProperties(authorRecord,author);
        return authorRepository.save(author);
    }

    public Object saveBooksOfAuthor(UUID id,
                                        AuthorBooksRecord authorBooksRecord){
        if(authorBooksRecord == null){
            return "Insert values to save in the system.";
        }
        Optional<Author> author = authorRepository.findById(id);
        if(author.isEmpty()){
            return "Author not found";
        }
        Set<Book> books = new HashSet<>();
        BeanUtils.copyProperties(authorBooksRecord,books);
        BeanUtils.copyProperties(books,author);
        return author;
    }

    @Transactional
    public Object listBooksByAuthor(UUID id){
        Optional<Author> authorO = authorRepository.findById(id);
        if(authorO.isEmpty()){
            return "Author not found.";
        }
        Set<Book> books =  authorO.get().getBooks();
        if(books.isEmpty()){
            return "Author don't have books yet.";
        }
        return books;
    }

    @Transactional
    public Object listAllAuthors(){
        List<Author> authors = authorRepository.findAll();
        if(authors.isEmpty()){
            return "There aren't authors in system.";
        }
        return authors;
    }

    @Transactional
    public Object listOneAuthor(UUID id){
        Optional<Author> authorO = authorRepository.findById(id);
        if(authorO.isEmpty()){
            return "Author not found";
        }
        return authorO.get();
    }

    @Transactional
    public String deleteAuthor(UUID id){
        Optional<Author> authorO = authorRepository.findById(id);
        if(authorO.isEmpty()){
            return "Author not found.";
        }
        authorRepository.delete(authorO.get());
        return "Delete author was doing with success.";
    }

    @Transactional
    public String deleteAuthors(List<UUID> ids){
        UUID idAuthor = null;
        try {
            for (UUID id : ids) {
                Optional<Author> authorO = authorRepository.findById(id);
                if (authorO.isEmpty()) {
                    idAuthor = id;
                    throw new Exception();
                }
                authorRepository.delete(authorO.get());
            }
        }catch(Exception e){
            return "Author with ID = " + idAuthor + ". Not found.";
        }
        return "The process of delete authors was success.";
    }

    @Transactional
    public Object updateAuthor(UUID id, AuthorRecord authorRecord){
        Optional<Author> authorO = authorRepository.findById(id);
        if(authorO.isEmpty()){
            return "Author not found";
        }
        Author author = authorO.get();
        BeanUtils.copyProperties(authorRecord,author);
        return authorRepository.save(author);
    }



}
