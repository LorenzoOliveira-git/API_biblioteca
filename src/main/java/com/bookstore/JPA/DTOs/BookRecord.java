package com.bookstore.JPA.DTOs;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;


public record BookRecord(@NotBlank String title, UUID publisher_id, Set<UUID> authorIds
        , String reviewComment) {
}
