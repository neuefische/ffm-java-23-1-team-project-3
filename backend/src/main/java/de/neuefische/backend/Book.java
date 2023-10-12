package de.neuefische.backend;

import lombok.With;

@With
public record Book(
        String id,
        String title,
        String author,
        String description,
        String publisher,
        String isbn,
        String coverUrl
) {
}
