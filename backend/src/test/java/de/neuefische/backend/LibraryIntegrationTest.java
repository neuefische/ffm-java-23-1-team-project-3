package de.neuefische.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LibraryRepository libraryRepository;

	@Test
	@DirtiesContext
	void whenGetAllBooks_performsOnEmptyRepo_returnsEmptyJsonArray() throws Exception {
		// Given

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.get("/api/books")
				)

				// Then
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}

	@Test
	@DirtiesContext
	void whenGetAllBooks_performsOnFilledRepo_returnsRepoContent() throws Exception {
		// Given
		libraryRepository.save(new Book("id1", "Title 1", "Author 1"));
		libraryRepository.save(new Book("id2", "Title 2", "Author 2"));
		libraryRepository.save(new Book("id3", "Title 3", "Author 3"));
		libraryRepository.save(new Book("id4", "Title 4", "Author 4"));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.get("/api/books")
				)

				// Then
				.andExpect(status().isOk())
				.andExpect(content().json("""
					[
						{ "id": "id1", "title": "Title 1", "author": "Author 1" },
						{ "id": "id2", "title": "Title 2", "author": "Author 2" },
						{ "id": "id3", "title": "Title 3", "author": "Author 3" },
						{ "id": "id4", "title": "Title 4", "author": "Author 4" }
					]
				"""));
	}

	@Test
	@DirtiesContext
	void removeBookTest() throws Exception {
		// Given
		libraryRepository.save(new Book("1", "My new book", "Me"));

		// When
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1"))
				// Then
				.andExpect(status().isOk());
	}


	@Test
	@DirtiesContext
	void whenUpdateProduct_getsInvalidID_returnsBadRequest() throws Exception {
		// Given
		libraryRepository.save(new Book("id1", "Title 1", "Author 1"));
		libraryRepository.save(new Book("id2", "Title 2", "Author 2"));
		libraryRepository.save(new Book("id3", "Title 3", "Author 3"));
		libraryRepository.save(new Book("id4", "Title 4", "Author 4"));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.put("/api/books/id1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
							{ "id": "id2", "title": "Title 1B", "author": "Author 1B" }
						""")
				)

				// Then
				.andExpect(status().isBadRequest());
	}

	@Test
	@DirtiesContext
	void whenUpdateProduct_getsUnknownID_returnsNotFound() throws Exception {
		// Given
		libraryRepository.save(new Book("id1", "Title 1", "Author 1"));
		libraryRepository.save(new Book("id2", "Title 2", "Author 2"));
		libraryRepository.save(new Book("id3", "Title 3", "Author 3"));
		libraryRepository.save(new Book("id4", "Title 4", "Author 4"));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.put("/api/books/id10")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
							{ "id": "id10", "title": "Title 1B", "author": "Author 1B" }
						""")
				)

				// Then
				.andExpect(status().isNotFound());
	}

	@Test
	@DirtiesContext
	void whenUpdateProduct_getsValidID_returnsChangedBook() throws Exception {
		// Given
		libraryRepository.save(new Book("id1", "Title 1", "Author 1"));
		libraryRepository.save(new Book("id2", "Title 2", "Author 2"));
		libraryRepository.save(new Book("id3", "Title 3", "Author 3"));
		libraryRepository.save(new Book("id4", "Title 4", "Author 4"));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.put("/api/books/id1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
							{ "id": "id1", "title": "Title 1B", "author": "Author 1B" }
						""")
				)

				// Then
				.andExpect(status().isOk())
				.andExpect(content().json("""
							{ "id": "id1", "title": "Title 1B", "author": "Author 1B" }
				"""));
	}
}
