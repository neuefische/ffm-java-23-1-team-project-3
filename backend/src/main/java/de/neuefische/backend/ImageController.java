package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
	) {
		try {
			return imageService.uploadFromURL(id, url);
		} catch (IOException e) {
			throw new ControllerException(
					HttpStatus.INTERNAL_SERVER_ERROR,
					"Error while uploading image: " + e.getMessage()
			);
		}
	}

	@PostMapping("/{id}/setCoverByFile")
	@ResponseStatus(HttpStatus.CREATED)
	public String uploadFromFile(
			@PathVariable String id,
			@RequestParam("file") MultipartFile file
	) {
		if (file == null)
			throw new ControllerException(
					HttpStatus.BAD_REQUEST, "Request contains no file."
			);

		try {
			return imageService.uploadFromFile(id, file);
		} catch (IOException e) {
			throw new ControllerException(
					HttpStatus.INTERNAL_SERVER_ERROR,
					"Error while uploading image: " + e.getMessage()
			);
		}
	}

	public static class ControllerException extends RuntimeException {

		@NonNull
		private final HttpStatusCode status;

		ControllerException(@NonNull HttpStatusCode status, @NonNull String message) {
			super(message);
			this.status = status;
		}
	}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleException(NoSuchElementException ex) {
		String message = "NoSuchElementException: %s".formatted(ex.getMessage());
		log.error(message);
		return new ErrorMessage(message);
	}

	@ExceptionHandler(ControllerException.class)
	public ResponseEntity<ErrorMessage> handleException(ControllerException ex) {
		String message = "ControllerException[%s]: %s".formatted(ex.status.toString(), ex.getMessage());
		log.error(message);
		return ResponseEntity
				.status(ex.status)
				.body(new ErrorMessage(message));
	}

}
