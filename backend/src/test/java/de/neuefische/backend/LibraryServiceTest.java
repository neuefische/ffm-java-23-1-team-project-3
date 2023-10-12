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
	private TimestampService timestampService;

	@BeforeEach
	void setUp() {
		libraryRepository = mock(LibraryRepository.class);
		timestampService = mock(TimestampService.class);
		libraryService = new LibraryService(libraryRepository,timestampService);
	}

	@Test
	void whenGetAllBooks_calledWithEmptyRepo_returnEmptyList() {
		// Given
		when(libraryRepository.findAll()).thenReturn(List.of());
		when(timestampService.getCurrentTimestamp()).thenReturn(new Timestamp("test", "<TestTimestamp>"));

		// When
		DatedBookList actual = libraryService.getAllBooks();

		// Then
		verify(libraryRepository).findAll();
		verify(timestampService).getCurrentTimestamp();

		DatedBookList expected = new DatedBookList(
				List.of(),
				new Timestamp("test", "<TestTimestamp>")
		);
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
		when(timestampService.getCurrentTimestamp()).thenReturn(new Timestamp("test", "<TestTimestamp>"));

		// When
		DatedBookList actual = libraryService.getAllBooks();

		// Then
		verify(libraryRepository).findAll();
		verify(timestampService).getCurrentTimestamp();

		DatedBookList expected = new DatedBookList(
				List.of(
						new Book("123","Title1","Author1"),
						new Book("124","Title2","Author2"),
						new Book("125","Title3","Author3"),
						new Book("126","Title4","Author4"),
						new Book("127","Title5","Author5"),
						new Book("128","Title6","Author6")
				),
				new Timestamp("test", "<TestTimestamp>")
		);
		assertEquals(expected, actual);
	}

	@Test
	void findBookById_Exist() {
		//GIVEN
		String id = "12";
		Book book12 = new Book(id,"title 12","author 12");

		when(libraryRepository.findById(id)).thenReturn(Optional.of(book12));

		//WHEN
		Book actual = libraryService.getBookById(id);

		//THEN
		Book expected = new Book("12","title 12","author 12");
		verify(libraryRepository).findById(id);
		assertEquals(expected,actual);
	}

	@Test
	void findBookById_NotExist(){
		//GIVEN
		String id ="33";

		when(libraryRepository.findById(id)).thenReturn(Optional.empty());
		//WHEN
		//THEN
		assertThrows(NoSuchElementException.class, ()->libraryService.getBookById(id));
	}


	@Test
	void deleteBook() {
		// Given

		// When
		libraryService.removeBook("id");

		// Then
		verify(libraryRepository).deleteById("id");
		verify(timestampService).setTimestampToNow();
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
		verify(timestampService).setTimestampToNow();
		Book expected = new Book("456", "Title 1", "Author 1");
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
		verify(libraryRepository).save(new Book(null,"Title6","Author6"));
		verify(timestampService).setTimestampToNow();
		Book expected = new Book("128","Title6","Author6");
		assertEquals(expected, actual);
	}
}