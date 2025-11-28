package com.bookstore.JPA.DTOs.Author;


import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record AuthorRecord(@NotBlank String name, Set<UUID> bookIds) {
}
