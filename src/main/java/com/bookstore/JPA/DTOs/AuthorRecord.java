package com.bookstore.JPA.DTOs;


import java.util.Set;
import java.util.UUID;

public record AuthorRecord(String name, Set<UUID> bookIds) {
}
