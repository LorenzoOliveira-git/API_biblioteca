package com.bookstore.JPA.DTOs.Author;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ResponseAuthorRecord(@NotBlank UUID id, @NotBlank String name) {
}
