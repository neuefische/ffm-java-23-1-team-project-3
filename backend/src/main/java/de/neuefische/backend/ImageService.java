package de.neuefische.backend;

import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

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
		cloudinary = cloudinaryUrl.isEmpty() ? null : new Cloudinary(cloudinaryUrl);
		this.cloudinaryFolder = cloudinaryFolder;
		this.libraryRepository = libraryRepository;
		log.info("Cloudinary Folder: \"%s\"".formatted(cloudinaryFolder));
	}

	public String uploadFromURL(@NonNull String id, @NonNull String url) throws IOException {
		return uploadObject(id, url, str -> str);
	}

	public String uploadFromFile(@NonNull String id, @NonNull MultipartFile file) throws IOException {
		return uploadObject(id, file.getBytes(), bytes -> {
			String base64String = Base64.getEncoder().encodeToString(bytes);
			return "data:"+file.getContentType()+";base64,"+base64String;
		});
	}

	private <S> String uploadObject(String id, S source, Function<S,String> getReplacementUrl) throws IOException {
		if (isIdUnknown(id))
			throw new NoSuchElementException("Can't set cover of book with ID \"%s\": Unknown ID".formatted(id));

		Map<String, Object> params = Map.of("public_id", cloudinaryFolder +"/"+ id);

		@SuppressWarnings("unchecked")
		Map<String, Object> result = cloudinary!=null
				? cloudinary.uploader().upload(source, params)
				: Map.of( "secure_url", getReplacementUrl.apply(source));
		return result.get("secure_url").toString();
	}

	private boolean isIdUnknown(String id) {
		return libraryRepository.findById(id).isEmpty();
	}
}
