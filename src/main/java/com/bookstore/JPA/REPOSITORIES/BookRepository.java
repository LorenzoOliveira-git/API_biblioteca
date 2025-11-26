package com.bookstore.JPA.REPOSITORIES;

import com.bookstore.JPA.MODELs.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

//    Exemplos de uso
//    Book findBookByTitle(String title);
//
//    @Query(value = "SELECT * FROM tb_book WHERE publisher_id = :id", nativeQuery = true)
//    List<Book> findBooksByPublisherId(@Param("id") UUID id);
}
