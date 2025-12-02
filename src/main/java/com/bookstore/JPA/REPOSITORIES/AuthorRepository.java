package com.bookstore.JPA.REPOSITORIES;

import com.bookstore.JPA.MODELs.Author;
import com.bookstore.JPA.MODELs.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    @Query("SELECT a FROM Author a WHERE a.name like :name")
    Optional<Author> findByName(@Param("name") String name);
}
