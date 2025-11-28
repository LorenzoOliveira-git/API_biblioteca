package com.bookstore.JPA.DTOs.Author;

import com.bookstore.JPA.MODELs.Book;
import jakarta.persistence.SecondaryTable;

import java.util.Set;

public record AuthorBooksRecord(Set<Book> books) {
}
