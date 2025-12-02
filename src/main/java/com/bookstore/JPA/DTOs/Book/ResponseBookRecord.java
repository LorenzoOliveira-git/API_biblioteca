package com.bookstore.JPA.DTOs.Book;

import com.bookstore.JPA.MODELs.Review;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ResponseBookRecord(@NotBlank UUID id,@NotNull String title,
                                 Review review){
}
