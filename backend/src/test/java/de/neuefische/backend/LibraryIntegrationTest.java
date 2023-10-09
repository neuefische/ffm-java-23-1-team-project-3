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
public class LibraryIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	LibraryRepository libraryRepository;

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
	void removeBookTest() throws Exception {
		libraryRepository.save(new Book("1", "My new book", "Me"));

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
                            {
                                "id": "1",
                                "title": "My new book",
                                "author": "Me"
                            }
                            """))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").doesNotExist());

	}
}
