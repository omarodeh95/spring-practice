package dev.learnkafka.domain;

import dev.learnkafka.domain.Book;

public record LibraryEvent(
    Integer libraryEventId,
    LibraryEventType libraryEventType,
    Book book
) {
}
