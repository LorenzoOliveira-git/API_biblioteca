package com.bookstore.JPA.REPOSITORIES;

import com.bookstore.JPA.MODELs.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    @Query("SELECT a FROM Author a JOIN FETCH a.books WHERE a.id = :id")
    Author findByIdWithBooks(@Param("id") UUID id);
}
