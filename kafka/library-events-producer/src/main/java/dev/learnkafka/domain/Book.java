package dev.learnkafka.domain;

public record Book(
    Integer bookId,
    String bookName,
    String bookAuthor
) {
}
