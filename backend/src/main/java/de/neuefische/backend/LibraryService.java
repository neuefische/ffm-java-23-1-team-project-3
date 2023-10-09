package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public List<Book> getAllBooks() {
        return libraryRepository.findAll();
    }

    public Book addBook(Book newBook) {
        String id = UUID.randomUUID().toString();

        Book book = new Book(
                id,
                newBook.title(),
                newBook.author()
        );
        return libraryRepository.save(book);
    }
}
