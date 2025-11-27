package com.bookstore.JPA.DTOs;

import com.bookstore.JPA.MODELs.Review;

import java.util.UUID;

public record BookSummaryRecord (UUID id, String title, Review review){
}
