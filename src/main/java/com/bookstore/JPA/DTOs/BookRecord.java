package com.bookstore.JPA.DTOs;

import java.util.Set;
import java.util.UUID;


public record BookRecord(String title, UUID publisher_id, Set<UUID> authorIds
        ,String reviewComment) {
}
