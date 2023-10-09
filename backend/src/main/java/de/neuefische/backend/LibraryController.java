package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping
    public List<Book> allBooks(){
        return libraryService.getAllBooks();
    }

    @PutMapping("{id}")
    public Book updateBook(@PathVariable String id, @RequestBody Book book){
        return libraryService.updateBook(id, book);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleIllegalArgumentException(IllegalArgumentException ex) {
        System.err.printf("IllegalArgumentException: %s%n", ex.getMessage());
        return new ErrorMessage("IllegalArgumentException: %s".formatted(ex.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNoSuchElementException(NoSuchElementException ex) {
        System.err.printf("NoSuchElementException: %s%n", ex.getMessage());
        return new ErrorMessage("NoSuchElementException: %s".formatted(ex.getMessage()));
    }
}
