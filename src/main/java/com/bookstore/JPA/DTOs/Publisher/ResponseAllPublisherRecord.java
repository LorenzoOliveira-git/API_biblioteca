package com.bookstore.JPA.DTOs.Publisher;

import com.bookstore.JPA.DTOs.Book.ResponseBookRecord;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ResponseAllPublisherRecord(@NotBlank UUID id,
                                         @NotNull String name,
                                         List<ResponseBookRecord> books) {
}
