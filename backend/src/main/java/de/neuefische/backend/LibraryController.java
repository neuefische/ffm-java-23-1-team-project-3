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

    @GetMapping("/{id}")
    public Book getBookByID(@PathVariable String id){
        return libraryService.getBookById(id);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNoSuchElementException(NoSuchElementException ex) {
        return new ErrorMessage("This book is not available.. " + ex.getMessage());
    }


}
