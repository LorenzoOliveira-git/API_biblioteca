package com.bookstore.JPA.REPOSITORIES;

import com.bookstore.JPA.MODELs.AuthorBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorBookRepository extends JpaRepository<AuthorBook, UUID> {
    List<AuthorBook> findAuthorBooksByAuthorId(UUID authorId);

    List<AuthorBook> findByAuthorId(UUID authorId);

    List<AuthorBook> findBookIdByAuthorId(UUID authorId);

    List<UUID> findBookIdByAuthorId(UUID authorId);
}
