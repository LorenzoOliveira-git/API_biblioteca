package com.bookstore.JPA.DTOs.Review;

import com.bookstore.JPA.MODELs.Book;
import jakarta.validation.constraints.NotBlank;

public record ReviewRecord(@NotBlank String comment) {
}
