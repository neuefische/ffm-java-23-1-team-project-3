package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public List<Book> getAllBooks() {
        return libraryRepository.findAll();
    }

    public void removeBook(String id) {
        libraryRepository.deleteById(id);
    }

    public Book updateBook(@NonNull String id, @NonNull Book book) {
        if (!id.equals(book.id()))
            throw new IllegalArgumentException("updateBook( id:%s, book:{ id:%s } ) -> given Id and Id of book are different".formatted(id, book.id()));

        Optional<Book> existingBook = libraryRepository.findById(id);
        if (existingBook.isEmpty())
            throw new NoSuchElementException("updateBook( id:%s, book:{ id:%s } ) -> Can't find a book with Id \"%s\"".formatted(id, book.id(), book.id()));

        return libraryRepository.save(book);
    }
}
