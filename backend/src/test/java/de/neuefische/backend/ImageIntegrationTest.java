package de.neuefische.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ImageIntegrationTest {

	@MockBean
	ClientRegistrationRepository clientRegistrationRepository;

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
		libraryRepository.save(new Book("id1", "Title 1", "Author 1", "Desc 1", "Publisher 1", "ISBN 1", "URL 1", false));

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
		libraryRepository.save(new Book("id1", "Title 1", "Author 1", "Desc 1", "Publisher 1", "ISBN 1", "URL 1", false));

		// When
		mockMvc
				.perform(MockMvcRequestBuilders
						.multipart(HttpMethod.POST, "/api/books/id1/setCoverByFile")
						.file(new MockMultipartFile("file", "testimage.png", MediaType.IMAGE_PNG_VALUE, new byte[]{1, 2, 3, 4, 5, 6}))
				)

				// Then
				.andExpect(status().isCreated())
				.andExpect(content().string("data:"+ MediaType.IMAGE_PNG_VALUE +";base64,"+ Base64.getEncoder().encodeToString(new byte[]{1, 2, 3, 4, 5, 6})));
	}
}