package com.bookstore.JPA.DTOs;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UUIDRecord(@NotBlank UUID id) {
}
