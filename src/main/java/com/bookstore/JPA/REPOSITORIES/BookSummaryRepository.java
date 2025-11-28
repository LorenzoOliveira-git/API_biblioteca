package com.bookstore.JPA.REPOSITORIES;


import com.bookstore.JPA.DTOs.Book.BookSummaryRecord;
import com.bookstore.JPA.MODELs.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface BookSummaryRepository extends JpaRepository<Book,
        UUID> {
    @Query("SELECT new com.bookstore.JPA.DTOs.Book.BookSummaryRecord(b.id,b" +
            ".title,b" +
            ".review) FROM Book b")
    List<BookSummaryRecord> findBookSummary();

    @Query("SELECT new com.bookstore.JPA.DTOs.Book.BookSummaryRecord(b.id,b" +
            ".title,b" +
            ".review) FROM Book b WHERE b.id = :id")
    BookSummaryRecord findBookSummary(@Param("id") UUID id);
}
