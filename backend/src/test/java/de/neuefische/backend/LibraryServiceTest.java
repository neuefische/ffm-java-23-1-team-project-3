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
				new Book("123", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1"),
				new Book("124", "Title2", "Author2", "Desc2", "Publisher2", "ISBN2", "URL2"),
				new Book("125", "Title3", "Author3", "Desc3", "Publisher3", "ISBN3", "URL3"),
				new Book("126", "Title4", "Author4", "Desc4", "Publisher4", "ISBN4", "URL4"),
				new Book("127", "Title5", "Author5", "Desc5", "Publisher5", "ISBN5", "URL5"),
				new Book("128", "Title6", "Author6", "Desc6", "Publisher6", "ISBN6", "URL6")
		));
		when(timestampService.getCurrentTimestamp()).thenReturn(new Timestamp("test", "<TestTimestamp>"));

		// When
		DatedBookList actual = libraryService.getAllBooks();

		// Then
		verify(libraryRepository).findAll();
		verify(timestampService).getCurrentTimestamp();

		DatedBookList expected = new DatedBookList(
				List.of(
						new Book("123", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1"),
						new Book("124", "Title2", "Author2", "Desc2", "Publisher2", "ISBN2", "URL2"),
						new Book("125", "Title3", "Author3", "Desc3", "Publisher3", "ISBN3", "URL3"),
						new Book("126", "Title4", "Author4", "Desc4", "Publisher4", "ISBN4", "URL4"),
						new Book("127", "Title5", "Author5", "Desc5", "Publisher5", "ISBN5", "URL5"),
						new Book("128", "Title6", "Author6", "Desc6", "Publisher6", "ISBN6", "URL6")
				),
				new Timestamp("test", "<TestTimestamp>")
		);
		assertEquals(expected, actual);
	}

	@Test
	void findBookById_Exist() {
		//GIVEN
		String id = "12";
		Book book12 = new Book(id, "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1");

		when(libraryRepository.findById(id)).thenReturn(Optional.of(book12));

		//WHEN
		Book actual = libraryService.getBookById(id);

		//THEN
		Book expected = new Book("12", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1");
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
		Executable executable = () -> libraryService.updateBook(id, new Book(bookId, "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1"));

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
		Executable executable = () -> libraryService.updateBook("456", new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1"));

		// Then
		assertThrows(NoSuchElementException.class, executable);
		verify(libraryRepository).findById("456");
	}

	@Test
	void whenUpdateProduct_getsValidID_returnsChangedBook() {
		// Given
		when(libraryRepository.findById("456")).thenReturn(
				Optional.of(new Book("456", "TitleA", "AuthorA", "DescA", "PublisherA", "ISBN A", "URL A"))
		);
		when(libraryRepository.save(new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1"))).thenReturn(
				new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1")
		);

		// When
		Book actual = libraryService.updateBook("456", new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1"));

		// Then
		verify(libraryRepository).findById("456");
		verify(libraryRepository).save(new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1"));
		verify(timestampService).setTimestampToNow();
		Book expected = new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1");
		assertEquals(expected, actual);
	}

	@Test
	void addBook() {
		//GIVEN
		when(libraryRepository.save(new Book(null, "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1")))
				.thenReturn(new Book("128", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1"));

		//WHEN
		Book actual = libraryService.addBook(new Book("12345678", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1"));

		//THEN
		verify(libraryRepository).save(new Book(null, "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1"));
		verify(timestampService).setTimestampToNow();
		Book expected = new Book("128", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1");
		assertEquals(expected, actual);
	}
}