package de.neuefische.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

	private LibraryRepository libraryRepository;
	private LibraryService libraryService;

	@BeforeEach
	void setUp() {
		libraryRepository = mock(LibraryRepository.class);
		libraryService = new LibraryService(libraryRepository);
	}

	@Test
	void whenGetAllBooks_calledWithEmptyRepo_returnEmptyList() {
		// Given
		when(libraryRepository.findAll()).thenReturn(List.of());

		// When
		List<Book> actual = libraryService.getAllBooks();

		// Then
		verify(libraryRepository).findAll();
		List<Book> expected = List.of();
		assertEquals(expected, actual);
	}

	@Test
	void whenGetAllBooks_calledWithNonEmptyRepo_returnListOfRepoContent() {
		// Given
		when(libraryRepository.findAll()).thenReturn(List.of(
				new Book("123","Title1","Author1"),
				new Book("124","Title2","Author2"),
				new Book("125","Title3","Author3"),
				new Book("126","Title4","Author4"),
				new Book("127","Title5","Author5"),
				new Book("128","Title6","Author6")
		));

		// When
		List<Book> actual = libraryService.getAllBooks();

		// Then
		verify(libraryRepository).findAll();
		List<Book> expected = List.of(
				new Book("123","Title1","Author1"),
				new Book("124","Title2","Author2"),
				new Book("125","Title3","Author3"),
				new Book("126","Title4","Author4"),
				new Book("127","Title5","Author5"),
				new Book("128","Title6","Author6")
		);
		assertEquals(expected, actual);
	}

	@Test
	void whenUpdateProduct_getsInvalidIDs_throwException() {
		// Given
		String id = "456";
		String bookId = "457";

		// When
		Executable executable = () -> libraryService.updateBook(id, new Book(bookId, "Title 1", "Author 1"));

		// Then
		assertThrows(IllegalArgumentException.class, executable);
	}

	@Test
	void whenUpdateProduct_getsUnknownID_throwException() {
		// Given
		when(libraryRepository.findById("456")).thenReturn(
				Optional.empty()
		);

		// When
		Executable executable = () -> libraryService.updateBook("456", new Book("456", "Title 1", "Author 1"));

		// Then
		assertThrows(NoSuchElementException.class, executable);
		verify(libraryRepository).findById("456");
	}

	@Test
	void whenUpdateProduct_getsValidID_returnsChangedBook() {
		// Given
		when(libraryRepository.findById("456")).thenReturn(
				Optional.of(new Book("456", "Title A", "Author A"))
		);
		when(libraryRepository.save(new Book("456", "Title 1", "Author 1"))).thenReturn(
				new Book("456", "Title 1", "Author 1")
		);

		// When
		Book actual = libraryService.updateBook("456", new Book("456", "Title 1", "Author 1"));

		// Then
		verify(libraryRepository).findById("456");
		verify(libraryRepository).save(new Book("456", "Title 1", "Author 1"));
		Book expected = new Book("456", "Title 1", "Author 1");
		assertEquals(expected, actual);
	}

}