package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
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

    public Book getBookById(String id) {

        Optional<Book> optionalBook = libraryRepository.findById(id);
        if(optionalBook.isPresent()){
            return optionalBook.get();
        } else {
            throw new NoSuchElementException("Buch nicht gefunden");
        }

     //        return libraryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Buch nicht gefunden"));

    }



}
