package de.neuefische.backend;

import java.time.ZonedDateTime;

public record Timestamp(String id, String timestamp) {
	public static Timestamp now() {
		return new Timestamp(
			"<singleton>",
			ZonedDateTime.now().toString()
		);
	}
}
