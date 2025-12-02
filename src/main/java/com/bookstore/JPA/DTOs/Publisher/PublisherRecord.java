package com.bookstore.JPA.DTOs.Publisher;


import com.bookstore.JPA.MODELs.Book;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record PublisherRecord(@NotBlank String name, List<UUID> booksIds) {
}
