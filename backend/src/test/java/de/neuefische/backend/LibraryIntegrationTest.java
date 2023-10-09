package de.neuefische.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
	void getBookByID_ifFound() throws Exception {
		//GIVEN
		String id= "1";
		Book book = new Book(id,"title 1","author 1");
		libraryRepository.save(book);
		//WHEN
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/"+ id))


				//THEN
				.andExpect(status().isOk())
				.andExpect(content().json("""
                        
                          {
                          "id": "1",
                          "title": "title 1",
                          "author": "author 1"
                          }

                        
"""));

	}

	/*@Test
	@DirtiesContext
	void getBookByID_ifNotFound() throws Exception {
		//GIVEN
		String id= "3";
		//WHEN
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/"+ id))


				//THEN
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").value("Buch nicht gefunden"));


	}
*/

}

