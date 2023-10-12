package de.neuefische.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/testupload")
@RequiredArgsConstructor
public class TestUploadController {

	@PostMapping
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		if (file != null) {
			System.out.println();
			System.out.printf("Name: %s%n", file.getName());
			System.out.printf("Size: %d%n", file.getSize());
			System.out.printf("ContentType: %s%n", file.getContentType());
			System.out.printf("OriginalFilename: %s%n", file.getOriginalFilename());
			try {
				byte[] bytes = file.getBytes();
				System.out.printf("content: %d bytes%n", bytes.length);
				return ResponseEntity
						.ok("Datei erfolgreich hochgeladen: " + file.getOriginalFilename());
			} catch (IOException e) {
				return ResponseEntity
						.internalServerError()
						.body("Fehler beim Speichern der Datei: " + file.getOriginalFilename());
			}
		}

		return ResponseEntity
				.badRequest()
				.body("Keine Datei ausgewählt.");
	}

}
