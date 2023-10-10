package de.neuefische.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
	void addBook() {
		//GIVEN
		when(libraryRepository.save(
				new Book(null,"Title6","Author6"))).thenReturn(
				new Book("128","Title6","Author6"));

		//WHEN
		Book actual = libraryService.addBook(new Book("12345678","Title6","Author6"));

		//THEN
		Book expected = new Book("128","Title6","Author6");
		verify(libraryRepository).save(new Book(null,"Title6","Author6"));
		assertEquals(expected, actual);
	}
}