package de.neuefische.backend;

public record Book(
        String id, String title, String author, Boolean favorite
) {
}
