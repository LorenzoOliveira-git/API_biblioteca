package com.bookstore.JPA.DTOs.Review;

import com.bookstore.JPA.DTOs.Book.ResponseBookRecord;

import java.util.UUID;

public record ResponseReviewRecord(UUID id, String comment,
                                   ResponseBookRecord book) {
}
