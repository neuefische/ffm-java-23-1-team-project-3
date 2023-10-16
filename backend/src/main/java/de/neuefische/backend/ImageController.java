package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	@PostMapping("/{id}/setCoverByURL")
	@ResponseStatus(HttpStatus.CREATED)
	public String uploadFromURL(
			@PathVariable String id,
			@RequestBody String url
	) throws IOException
	{
		return imageService.uploadFromURL(id, url);
	}

	@PostMapping("/{id}/setCoverByFile")
	@ResponseStatus(HttpStatus.CREATED)
	public String uploadFromFile(
			@PathVariable String id,
			@RequestParam("file") MultipartFile file
	) throws IOException
	{
		return imageService.uploadFromFile(id, file);
	}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleException(NoSuchElementException ex) {
		String message = "NoSuchElementException: %s".formatted(ex.getMessage());
		log.error(message);
		return new ErrorMessage(message);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorMessage> handleException(IOException ex) {
		String message = "IOException while uploading image: %s".formatted(ex.getMessage());
		log.error(message);
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorMessage(message));
	}

}
