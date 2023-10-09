package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
