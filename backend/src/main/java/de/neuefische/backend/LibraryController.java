package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class LibraryController {

    private final LibraryService libraryService;
    private final TimestampService timestampService;

    @GetMapping
    public DatedBookList allBooks(){
        return libraryService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookByID(@PathVariable String id){
        return libraryService.getBookById(id);
    }
    @GetMapping("/search/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title){
        return libraryService.getBooksByTitle(title);
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

    @GetMapping("/state")
    public Timestamp getTimestampOfDB(){
        return timestampService.getCurrentTimestamp();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleIllegalArgumentException(IllegalArgumentException ex) {
        String messsage = "IllegalArgumentException: %s".formatted(ex.getMessage());
        log.error(messsage);
        return new ErrorMessage(messsage);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNoSuchElementException(NoSuchElementException ex) {
        String message = "NoSuchElementException: %s".formatted(ex.getMessage());
        log.error(message);
        return new ErrorMessage(message);
    }

}
