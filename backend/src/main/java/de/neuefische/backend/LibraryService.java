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
    private final TimestampService timestampService;

    public synchronized DatedBookList getAllBooks() {
        List<Book> books = libraryRepository.findAll();
        Timestamp timestamp = timestampService.getCurrentTimestamp();
        return new DatedBookList( books, timestamp );
    }

    public synchronized void removeBook(String id) {
        libraryRepository.deleteById(id);
        timestampService.setTimestampToNow();
    }

    public synchronized Book updateBook(@NonNull String id, @NonNull Book book) {
        if (!id.equals(book.id()))
            throw new IllegalArgumentException("updateBook( id:%s, book:{ id:%s } ) -> given Id and Id of book are different".formatted(id, book.id()));

        Optional<Book> existingBook = libraryRepository.findById(id);
        if (existingBook.isEmpty())
            throw new NoSuchElementException("updateBook( id:%s, book:{ id:%s } ) -> Can't find a book with Id \"%s\"".formatted(id, book.id(), book.id()));

        Book saved = libraryRepository.save(book);
        timestampService.setTimestampToNow();
        return saved;
    }

    public synchronized Book addBook(Book newBook) {
        Book bookToSave = newBook.withId(null);
        Book savedBook = libraryRepository.save(bookToSave);
        timestampService.setTimestampToNow();
        return savedBook;
    }

    public Book getBookById(String id) {

        Optional<Book> optionalBook = libraryRepository.findById(id);
        if(optionalBook.isPresent()){
            return optionalBook.get();
        } else {
            throw new NoSuchElementException("Buch nicht gefunden");
        }
    }
}
