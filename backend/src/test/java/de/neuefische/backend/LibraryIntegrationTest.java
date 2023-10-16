package de.neuefische.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryIntegrationTest {

/*
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LibraryRepository libraryRepository;
	@Autowired
	private TimestampRepository timestampRepository;

	@Test
	@DirtiesContext
	void whenGetAllBooks_performsOnEmptyRepo_returnsEmptyJsonArray() throws Exception {
		// Given
		timestampRepository.save(new Timestamp("test", "<TestTimestamp>"));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.get("/api/books")
				)

				// Then
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"books": [],
						"timestamp": {
							"id": "test",
							"timestamp": "<TestTimestamp>"
						}
					}
				"""));
	}

    @Test
    @DirtiesContext
    void getBookByID_ifFound() throws Exception {
        //GIVEN
        String id= "1";
        Book book = new Book(id,"Title 1","Author 1","Desc 1","Publisher 1","ISBN 1","URL 1", false);
        libraryRepository.save(book);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/"+ id))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
					{
						"id"         : "1",
						"title"      : "Title 1",
						"author"     : "Author 1",
						"description": "Desc 1",
						"publisher"  : "Publisher 1",
						"isbn"       : "ISBN 1",
						"coverUrl"   : "URL 1",
						"favorite"	 : false
					}
				"""));
    }

    @Test
    @DirtiesContext
    void getBookByID_ifNotFound_handleNoSuchElementException() throws Exception {
        //GIVEN
        String id= "3";

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/"+ id))

                //THEN
                .andExpect(status().isNotFound());
    }

	@Test
	@DirtiesContext
	void whenGetAllBooks_performsOnFilledRepo_returnsRepoContent() throws Exception {
		// Given
		libraryRepository.save(new Book("id1", "Title 1", "Author 1", "Desc 1", "Publisher 1", "ISBN 1", "URL 1", false));
		libraryRepository.save(new Book("id2", "Title 2", "Author 2", "Desc 2", "Publisher 2", "ISBN 2", "URL 2", false));
		libraryRepository.save(new Book("id3", "Title 3", "Author 3", "Desc 3", "Publisher 3", "ISBN 3", "URL 3", false));
		libraryRepository.save(new Book("id4", "Title 4", "Author 4", "Desc 4", "Publisher 4", "ISBN 4", "URL 4", false));
		timestampRepository.save(new Timestamp("test", "<TestTimestamp>"));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.get("/api/books")
				)

				// Then
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{
						"books": [
							{ "id": "id1", "title": "Title 1", "author": "Author 1", "description": "Desc 1", "publisher": "Publisher 1", "isbn": "ISBN 1", "coverUrl": "URL 1", "favorite": false },
							{ "id": "id2", "title": "Title 2", "author": "Author 2", "description": "Desc 2", "publisher": "Publisher 2", "isbn": "ISBN 2", "coverUrl": "URL 2", "favorite": false },
							{ "id": "id3", "title": "Title 3", "author": "Author 3", "description": "Desc 3", "publisher": "Publisher 3", "isbn": "ISBN 3", "coverUrl": "URL 3", "favorite": false },
							{ "id": "id4", "title": "Title 4", "author": "Author 4", "description": "Desc 4", "publisher": "Publisher 4", "isbn": "ISBN 4", "coverUrl": "URL 4", "favorite": false }
						],
						"timestamp": {
							"id": "test",
							"timestamp": "<TestTimestamp>"
						}
					}
				"""));
	}

	@Test
	@DirtiesContext
	void removeBookTest() throws Exception {
		// Given
		libraryRepository.save(new Book("1", "My new book", "Me", "Desc 1", "Publisher 1", "ISBN 1", "URL 1", false));

		// When
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1"))

				// Then
				.andExpect(status().isOk());
	}

    @Test
    @DirtiesContext
    void whenAddBooks_getsNewBooks_returnsBooks() throws Exception {
        // Given

        // When
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
							{ "title": "Title 1", "author": "Author 1", "description": "Desc 1", "publisher": "Publisher 1", "isbn": "ISBN 1", "coverUrl": "URL 1", "favorite": false }
						""")
                )

                // Then
                .andExpect(status().isCreated())
                .andExpect(content().json("""
					{ "title": "Title 1", "author": "Author 1", "description": "Desc 1", "publisher": "Publisher 1", "isbn": "ISBN 1", "coverUrl": "URL 1", "favorite": false }
				"""))
                .andExpect(jsonPath("$.id").isString());
    }

	@Test
	@DirtiesContext
	void whenUpdateProduct_getsInvalidID_returnsBadRequest() throws Exception {
		// Given
		libraryRepository.save(new Book("id1", "Title 1", "Author 1", "Desc 1", "Publisher 1", "ISBN 1", "URL 1", false));
		libraryRepository.save(new Book("id2", "Title 2", "Author 2", "Desc 2", "Publisher 2", "ISBN 2", "URL 2", false));
		libraryRepository.save(new Book("id3", "Title 3", "Author 3", "Desc 3", "Publisher 3", "ISBN 3", "URL 3", false));
		libraryRepository.save(new Book("id4", "Title 4", "Author 4", "Desc 4", "Publisher 4", "ISBN 4", "URL 4", false));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.put("/api/books/id1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
							{ "id": "id2", "title": "Title 1B", "author": "Author 1B", "description": "Desc 1", "publisher": "Publisher 1", "isbn": "ISBN 1", "coverUrl": "URL 1", "favorite": false }
						""")
				)

				// Then
				.andExpect(status().isBadRequest());
	}

	@Test
	@DirtiesContext
	void whenUpdateProduct_getsUnknownID_returnsNotFound() throws Exception {
		// Given
		libraryRepository.save(new Book("id1", "Title 1", "Author 1", "Desc 1", "Publisher 1", "ISBN 1", "URL 1", false));
		libraryRepository.save(new Book("id2", "Title 2", "Author 2", "Desc 2", "Publisher 2", "ISBN 2", "URL 2", false));
		libraryRepository.save(new Book("id3", "Title 3", "Author 3", "Desc 3", "Publisher 3", "ISBN 3", "URL 3", false));
		libraryRepository.save(new Book("id4", "Title 4", "Author 4", "Desc 4", "Publisher 4", "ISBN 4", "URL 4", false));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.put("/api/books/id10")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
							{ "id": "id10", "title": "Title 1B", "author": "Author 1B", "description": "Desc 1", "publisher": "Publisher 1", "isbn": "ISBN 1", "coverUrl": "URL 1", "favorite": false }
						""")
				)

				// Then
				.andExpect(status().isNotFound());
	}

	@Test
	@DirtiesContext
	void whenUpdateProduct_getsValidID_returnsChangedBook() throws Exception {
		// Given
		libraryRepository.save(new Book("id1", "Title 1", "Author 1", "Desc 1", "Publisher 1", "ISBN 1", "URL 1", false));
		libraryRepository.save(new Book("id2", "Title 2", "Author 2", "Desc 2", "Publisher 2", "ISBN 2", "URL 2", false));
		libraryRepository.save(new Book("id3", "Title 3", "Author 3", "Desc 3", "Publisher 3", "ISBN 3", "URL 3", false));
		libraryRepository.save(new Book("id4", "Title 4", "Author 4", "Desc 4", "Publisher 4", "ISBN 4", "URL 4", false));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.put("/api/books/id1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
							{ "id": "id1", "title": "Title 1B", "author": "Author 1B", "description": "Desc 1", "publisher": "Publisher 1", "isbn": "ISBN 1", "coverUrl": "URL 1", "favorite": false }
						""")
				)

				// Then
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{ "id": "id1", "title": "Title 1B", "author": "Author 1B", "description": "Desc 1", "publisher": "Publisher 1", "isbn": "ISBN 1", "coverUrl": "URL 1", "favorite": false }
				"""));
	}

	@Test
	@DirtiesContext
	void whenGetTimestampOfDB_isCalledOnEmptyTimestampRepo_returnsNothing() throws Exception {
		// Given

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.get("/api/books/state")
				)

				// Then
				.andExpect(status().isOk())
				.andExpect(content().string(""));
	}

	@Test
	@DirtiesContext
	void whenGetTimestampOfDB_isCalledOnNormalTimestampRepo_returnsTimestamp() throws Exception {
		// Given
		timestampRepository.save(new Timestamp("test", "<TestTimestamp>"));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.get("/api/books/state")
				)

				// Then
				.andExpect(status().isOk())
				.andExpect(content().json("""
					{ "id": "test", "timestamp": "<TestTimestamp>" }
				"""));
	}
*/

}
