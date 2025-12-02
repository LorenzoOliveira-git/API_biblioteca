package com.bookstore.JPA.DTOs.Publisher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ResponsePublisherRecord (@NotBlank UUID id,@NotNull String name){
}
