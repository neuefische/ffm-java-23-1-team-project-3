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
        String coverUrl,
        boolean favorite
) {
	public Book(String id, String title, String author, String description, String publisher, String isbn, String coverUrl) {
		this(id, title, author, description, publisher, isbn, coverUrl, false);
	}
}
