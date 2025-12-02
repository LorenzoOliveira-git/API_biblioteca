package com.bookstore.JPA.DTOs.Author;

import com.bookstore.JPA.DTOs.Book.ResponseBookRecord;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record ResponseAllAuthorRecord(@NotBlank UUID id, @NotBlank String name,
                                      Set<ResponseBookRecord> books){
}
