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

	private final LibraryService libraryService;

	@PostMapping("/{id}/setCoverByURL")
	@ResponseStatus(HttpStatus.CREATED)
	public String addBook(
			@PathVariable String id,
			@RequestBody String url
	) {
		if (libraryService.isIdUnknown(id))
			throw new NoSuchElementException("Can't set cover of book with ID \"%s\": Unknown ID".formatted(id));

		return url;
	}

	@PostMapping("/{id}/setCoverByFile")
	@ResponseStatus(HttpStatus.CREATED)
	public String handleFileUpload(
			@PathVariable String id,
			@RequestParam("file") MultipartFile file
	) {
		if (libraryService.isIdUnknown(id))
			throw new NoSuchElementException(
					"Can't set cover of book with ID \"%s\": Unknown ID".formatted(id)
			);

		if (file == null)
			throw new ControllerException(
					HttpStatus.BAD_REQUEST, "Keine Datei ausgewählt."
			);

		System.out.println();
		System.out.printf("Name: %s%n", file.getName());
		System.out.printf("Size: %d%n", file.getSize());
		System.out.printf("ContentType: %s%n", file.getContentType());
		System.out.printf("OriginalFilename: %s%n", file.getOriginalFilename());
		try {
			byte[] bytes = file.getBytes();
			System.out.printf("content: %d bytes%n", bytes.length);
			return "https://taskviewer.bootcamp-tools.de/assets/logo-black-c716199a.svg";
		}
		catch (IOException e) {
			throw new ControllerException(
					HttpStatus.INTERNAL_SERVER_ERROR,
					"Fehler beim Speichern der Datei: " + file.getOriginalFilename()
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
