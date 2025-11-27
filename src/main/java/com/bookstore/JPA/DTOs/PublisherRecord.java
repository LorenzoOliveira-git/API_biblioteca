package com.bookstore.JPA.DTOs;


import com.bookstore.JPA.MODELs.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record PublisherRecord(@NotBlank String name, Set<Book> books) {
}
