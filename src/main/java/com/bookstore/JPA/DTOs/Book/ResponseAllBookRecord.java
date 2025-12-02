package com.bookstore.JPA.DTOs.Book;

import com.bookstore.JPA.DTOs.Author.ResponseAuthorRecord;
import com.bookstore.JPA.DTOs.Publisher.ResponsePublisherRecord;
import com.bookstore.JPA.MODELs.Author;
import com.bookstore.JPA.MODELs.Publisher;
import com.bookstore.JPA.MODELs.Review;

import java.util.Set;
import java.util.UUID;

public record ResponseAllBookRecord(UUID id, String title,
                                    ResponsePublisherRecord publisher,
                                    Set<ResponseAuthorRecord> authors,
                                    Review review) {
}
