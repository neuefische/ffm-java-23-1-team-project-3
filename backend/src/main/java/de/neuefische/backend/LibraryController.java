package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping
    public DatedBookList allBooks(){
        return libraryService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookByID(@PathVariable String id){
        return libraryService.getBookById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book newBook){return libraryService.addBook(newBook);}

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        libraryService.removeBook(id);
    }

    @PutMapping("/{id}")
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
