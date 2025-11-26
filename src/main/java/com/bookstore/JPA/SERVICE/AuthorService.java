package com.bookstore.JPA.SERVICE;

import com.bookstore.JPA.DTOs.AuthorRecord;
import com.bookstore.JPA.MODELs.Author;
import com.bookstore.JPA.MODELs.AuthorBook;
import com.bookstore.JPA.MODELs.Book;
import com.bookstore.JPA.REPOSITORIES.AuthorBookRepository;
import com.bookstore.JPA.REPOSITORIES.AuthorRepository;
import com.bookstore.JPA.REPOSITORIES.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorService{

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorBookRepository authorBookRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Author saveAuthor(AuthorRecord authorRecord){
        Author author = new Author();
        author.setName(authorRecord.name());
        return authorRepository.save(author);
    }

    @Transactional
    public Set<Book> listBooksByAuthor(UUID id){
        List<UUID> authorBookList =
                authorBookRepository.findBookIdByAuthorId(id);
        return bookRepository.findAllById(authorBookList).stream().collect(Collectors.toSet());
    }

}
