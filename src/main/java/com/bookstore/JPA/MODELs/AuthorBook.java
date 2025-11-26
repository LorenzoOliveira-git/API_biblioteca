package com.bookstore.JPA.MODELs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="tb_author_book")
public class AuthorBook implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private UUID authorId;
    @Column(nullable = false, unique = true)
    private UUID bookId;

    public UUID getId() {return id;}

    public void setId(UUID id) {this.id = id;}

    public UUID getAuthorId() {return authorId;}

    public void setAuthorId(UUID authorId) {this.authorId = authorId;}

    public UUID getBookId() {return bookId;}

    public void setBookId(UUID bookId) {this.bookId = bookId;}
}
