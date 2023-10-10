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
	void whenAddBooks_getsNewBooks_returnsBooks() throws Exception {
		// Given

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.post("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{ \"title\":\"Title 1\", \"author\": \"Author1\"}")
				)

				// Then
				.andExpect(status().isCreated())
				.andExpect(content().json("{ \"title\":\"Title 1\", \"author\": \"Author1\" }"))
				.andExpect(jsonPath("$.id").isString());
	}
}
