package de.neuefische.backend;

import java.util.List;

public record DatedBookList(List<Book> books, Timestamp timestamp) {
}
