package de.neuefische.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ImageServiceTest {

	private LibraryRepository libraryRepository;
	private ImageService imageService;

	@BeforeEach
	void setUp() {
		libraryRepository = mock(LibraryRepository.class);
		imageService = new ImageService("", "", libraryRepository);
	}

	@Test
	void whenUploadFromURL_getsUnknownID_throwNoSuchElementException() {
		// Given
		when(libraryRepository.findById("TestId")).thenReturn(Optional.empty());

		// When
		Executable executable = () ->
				imageService.uploadFromURL("TestId", "TestURL");

		// Then
		assertThrows(NoSuchElementException.class, executable);
	}

	@Test
	void whenUploadFromURL_getsKnownID_returnURL() throws IOException {
		// Given
		when(libraryRepository.findById("TestId")).thenReturn(Optional.of(
				new Book("TestId", "", "", "", "", "", "")
		));

		// When
		String actual = imageService.uploadFromURL("TestId", "TestURL");

		// Then
		String expected = "TestURL";
		assertEquals(expected, actual);
	}

	@Test
	void whenUploadFromFile_getsUnknownID_throwNoSuchElementException() {
		// Given
		when(libraryRepository.findById("TestId")).thenReturn(Optional.empty());

		// When
		Executable executable = () ->
				imageService.uploadFromFile("TestId", new MockMultipartFile("TestFile", new byte[] { 1,2,3,4 }));

		// Then
		assertThrows(NoSuchElementException.class, executable);
	}

	@Test
	void whenUploadFromFile_getsKnownID_returnURL() throws IOException {
		// Given
		when(libraryRepository.findById("TestId")).thenReturn(Optional.of(
				new Book("TestId", "", "", "", "", "", "")
		));

		// When
		String actual = imageService.uploadFromFile("TestId", new MockMultipartFile("file", "testimage.png", MediaType.IMAGE_PNG_VALUE, new byte[]{1, 2, 3, 4}));

		// Then
		String expected = "data:"+ MediaType.IMAGE_PNG_VALUE +";base64,"+ Base64.getEncoder().encodeToString(new byte[]{1, 2, 3, 4});
		assertEquals(expected, actual);
	}
}