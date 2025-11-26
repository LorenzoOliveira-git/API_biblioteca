package com.bookstore.JPA.SERVICE;

import com.bookstore.JPA.DTOs.BookRecord;
import com.bookstore.JPA.MODELs.Book;
import com.bookstore.JPA.MODELs.Review;
import com.bookstore.JPA.REPOSITORIES.AuthorRepository;
import com.bookstore.JPA.REPOSITORIES.BookRepository;
import com.bookstore.JPA.REPOSITORIES.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @Transactional // serve para implementar a função de Roolback caso algo
    // de errado
    public Book saveBook(BookRecord bookRecord){

//        melhorar isso com verificação de erros
        Book book = new Book();
        book.setTitle(bookRecord.title());
        book.setPublisher(publisherRepository.findById(bookRecord.publisher_id()).get());
        book.setAuthors(authorRepository.findAllById(bookRecord.authorIds()).stream().collect(Collectors.toSet()));
//        Essa modelagem de .stream e .collect(Collectors.toSet()) serve para
//        transformar um List<> (retorno do metodo findAllById) em um Set<>,
//        ou seja, o .stream serve para modular os dados que estão vindo e o
//        .collect serve para transformar essa modularização em um Set (OBS:
//        esse Set foi instanciado a partir do metodo Collectors.toSet().

        Review review = new Review();
        review.setComment(bookRecord.reviewComment());
        review.setBook(book);
        book.setReview(review);

        return bookRepository.save(book);

    }
}
