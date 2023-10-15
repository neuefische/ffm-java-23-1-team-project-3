package de.neuefische.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ImageIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private LibraryRepository libraryRepository;

	@Test
	@DirtiesContext
	void whenUploadFromURL_getsUnknownID_returnsStatus404() throws Exception {
		// Given

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.post("/api/books/id1/setCoverByURL")
						.contentType(MediaType.TEXT_PLAIN)
						.content("TestURL")
				)

				// Then
				.andExpect(status().isNotFound());
	}

	@Test
	@DirtiesContext
	void whenUploadFromURL_getsKnownID_returnURL() throws Exception {
		// Given
		libraryRepository.save(new Book("id1", "Title 1", "Author 1", "Desc 1", "Publisher 1", "ISBN 1", "URL 1"));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.post("/api/books/id1/setCoverByURL")
						.contentType(MediaType.TEXT_PLAIN)
						.content("TestURL")
				)

				// Then
				.andExpect(status().isCreated())
				.andExpect(content().string("TestURL"));
	}

	@Test
	@DirtiesContext
	void whenUploadFromFile_getsUnknownID_returnsStatus404() throws Exception {
		// Given

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.multipart(HttpMethod.POST, "/api/books/id1/setCoverByFile")
						.file(new MockMultipartFile("file", new byte[] { 1,2,3,4,5,6 }))
				)

				// Then
				.andExpect(status().isNotFound());
	}

	@Test
	@DirtiesContext
	void whenUploadFromFile_getsKnownID_returnURL() throws Exception {
		// Given
		libraryRepository.save(new Book("id1", "Title 1", "Author 1", "Desc 1", "Publisher 1", "ISBN 1", "URL 1"));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.multipart(HttpMethod.POST, "/api/books/id1/setCoverByFile")
						.file(new MockMultipartFile("file", new byte[]{1, 2, 3, 4, 5, 6}))
				)

				// Then
				.andExpect(status().isCreated())
				.andExpect(content().string("https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Face-blush.svg/240px-Face-blush.svg.png"));
	}
}