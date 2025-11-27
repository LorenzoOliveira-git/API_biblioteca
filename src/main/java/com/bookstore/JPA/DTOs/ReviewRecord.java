package com.bookstore.JPA.DTOs;

import com.bookstore.JPA.MODELs.Book;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ReviewRecord(@JsonProperty UUID id, String title, Book book) {
}
