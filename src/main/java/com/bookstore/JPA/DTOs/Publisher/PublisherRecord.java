package com.bookstore.JPA.DTOs.Publisher;


import com.bookstore.JPA.MODELs.Book;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record PublisherRecord(@NotBlank String name, Set<Book> books) {
}
