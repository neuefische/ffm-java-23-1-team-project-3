package de.neuefische.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Collections.emptyList;
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
				new Book("123", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false),
				new Book("124", "Title2", "Author2", "Desc2", "Publisher2", "ISBN2", "URL2", false),
				new Book("125", "Title3", "Author3", "Desc3", "Publisher3", "ISBN3", "URL3", false),
				new Book("126", "Title4", "Author4", "Desc4", "Publisher4", "ISBN4", "URL4", false),
				new Book("127", "Title5", "Author5", "Desc5", "Publisher5", "ISBN5", "URL5", false),
				new Book("128", "Title6", "Author6", "Desc6", "Publisher6", "ISBN6", "URL6", false)
		));
		when(timestampService.getCurrentTimestamp()).thenReturn(new Timestamp("test", "<TestTimestamp>"));

		// When
		DatedBookList actual = libraryService.getAllBooks();

		// Then
		verify(libraryRepository).findAll();
		verify(timestampService).getCurrentTimestamp();

		DatedBookList expected = new DatedBookList(
				List.of(
						new Book("123", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false),
						new Book("124", "Title2", "Author2", "Desc2", "Publisher2", "ISBN2", "URL2", false),
						new Book("125", "Title3", "Author3", "Desc3", "Publisher3", "ISBN3", "URL3", false),
						new Book("126", "Title4", "Author4", "Desc4", "Publisher4", "ISBN4", "URL4", false),
						new Book("127", "Title5", "Author5", "Desc5", "Publisher5", "ISBN5", "URL5", false),
						new Book("128", "Title6", "Author6", "Desc6", "Publisher6", "ISBN6", "URL6", false)
				),
				new Timestamp("test", "<TestTimestamp>")
		);
		assertEquals(expected, actual);
	}

	@Test
	void findBookById_Exist() {
		//GIVEN
		String id = "12";
		Book book12 = new Book(id, "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false);

		when(libraryRepository.findById(id)).thenReturn(Optional.of(book12));

		//WHEN
		Book actual = libraryService.getBookById(id);

		//THEN
		Book expected = new Book("12", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false);
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
	void testFindBooksByTitle_If_TitleExistsAsExactMatch_Then_itReturnsAllBooksWithSimilarTitle(){
        //GIVEN
		String title = "titleExist";
		Book book = new Book("1",title,"author","Desc1", "Publisher1", "ISBN1", "URL1", false);
		List<Book> books = new ArrayList<>();
		books.add(book);
		when(libraryRepository.findByTitleRegexIgnoreCase(title)).thenReturn(books);

		//WHEN
		List<Book> actual = libraryService.getBooksByTitle(title);

		//THEN
		Book bookToSave = new Book("1","titleExist","author","Desc1", "Publisher1", "ISBN1", "URL1", false);
		List<Book> expected = List.of(bookToSave);
		verify(libraryRepository).findByTitleRegexIgnoreCase(title);
		assertEquals(expected,actual);
	}

	@Test
	void testFindBooksByTitle_If_TitleDoesNotMatch_Then_itReturnsAllBooksThatPartiallyContainTheTitle(){
        //GIVEN
		String title = "TitleNotExist";
		Book book1 = new Book("1","title1","author","Desc1", "Publisher1", "ISBN1", "URL1", false);
		Book book2 = new Book("2","title2","author","Desc1", "Publisher1", "ISBN1", "URL1", false);
		List<Book> books = new ArrayList<>();
		books.add(book1);
		books.add(book2);
		when(libraryRepository.findByTitleRegexIgnoreCase(title)).thenReturn(books);

		//WHEN
		List<Book> actual = libraryService.getBooksByTitle(title);

		//THEN
		Book bookToSave1 = new Book("1","title1","author","Desc1", "Publisher1", "ISBN1", "URL1", false);
		Book bookToSave2 =  new Book("2","title2","author","Desc1", "Publisher1", "ISBN1", "URL1", false);
		List<Book> expected = List.of(bookToSave1,bookToSave2);
		verify(libraryRepository).findByTitleRegexIgnoreCase(title);
		assertEquals(expected,actual);
	}
	@Test
	void testFindBooksByTitle_If_TitleNotExists_NeitherIdenticallyNorPartially_Then_itReturnsException(){
		//GIVEN
		String title = "TitleNotExistAtAll";

		when(libraryRepository.findByTitleRegexIgnoreCase(title)).thenReturn(emptyList());
		//WHEN
		//THEN
		assertThrows(NoSuchElementException.class, ()->libraryService.getBooksByTitle(title));
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
		Executable executable = () -> libraryService.updateBook(id, new Book(bookId, "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false));

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
		Executable executable = () -> libraryService.updateBook("456", new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false));

		// Then
		assertThrows(NoSuchElementException.class, executable);
		verify(libraryRepository).findById("456");
	}

	@Test
	void whenUpdateProduct_getsValidID_returnsChangedBook() {
		// Given
		when(libraryRepository.findById("456")).thenReturn(
				Optional.of(new Book("456", "TitleA", "AuthorA", "DescA", "PublisherA", "ISBN A", "URL A", false))
		);
		when(libraryRepository.save(new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false))).thenReturn(
				new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false)
		);

		// When
		Book actual = libraryService.updateBook("456", new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false));

		// Then
		verify(libraryRepository).findById("456");
		verify(libraryRepository).save(new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false));
		verify(timestampService).setTimestampToNow();
		Book expected = new Book("456", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false);
		assertEquals(expected, actual);
	}

	@Test
	void addBook() {
		//GIVEN
		when(libraryRepository.save(new Book(null, "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false)))
				.thenReturn(new Book("128", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false));

		//WHEN
		Book actual = libraryService.addBook(new Book("12345678", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false));

		//THEN
		verify(libraryRepository).save(new Book(null, "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false));
		verify(timestampService).setTimestampToNow();
		Book expected = new Book("128", "Title1", "Author1", "Desc1", "Publisher1", "ISBN1", "URL1", false);
		assertEquals(expected, actual);
	}
}