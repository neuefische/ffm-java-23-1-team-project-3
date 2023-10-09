package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping
    public List<Book> allBooks(){
        return libraryService.getAllBooks();
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        libraryService.removeBook(id);
    }

}


