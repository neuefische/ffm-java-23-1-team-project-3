package de.neuefische.backend;

import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class ImageService {

	private final String cloudinaryFolder;
	private final LibraryRepository libraryRepository;
	private final Cloudinary cloudinary;

	ImageService(
			@Value("${app.cloudinary.url}") String cloudinaryUrl,
			@Value("${app.cloudinary.folder}") String cloudinaryFolder,
			LibraryRepository libraryRepository
	) {
		cloudinary = new Cloudinary(cloudinaryUrl);
		this.cloudinaryFolder = cloudinaryFolder;
		this.libraryRepository = libraryRepository;
		log.info("Cloudinary Folder: %s".formatted(cloudinaryFolder));
	}

	private boolean isIdUnknown(String id) {
		return libraryRepository.findById(id).isEmpty();
	}

	public String uploadFromURL(@NonNull String id, @NonNull String url) throws IOException {
		if (isIdUnknown(id))
			throw new NoSuchElementException("Can't set cover of book with ID \"%s\": Unknown ID".formatted(id));

		Map<String, Object> params = Map.of("public_id", cloudinaryFolder +"/"+ id);

		@SuppressWarnings("unchecked")
		Map<String, Object> result = cloudinary.uploader().upload(url, params);
		return result.get("secure_url").toString();
	}

	public String uploadFromFile(@NonNull String id, @NonNull MultipartFile file) throws IOException {
		if (isIdUnknown(id))
			throw new NoSuchElementException("Can't set cover of book with ID \"%s\": Unknown ID".formatted(id));

		Map<String, Object> params = Map.of("public_id", cloudinaryFolder +"/"+ id);

		@SuppressWarnings("unchecked")
		Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), params);
		return result.get("secure_url").toString();
	}
}
