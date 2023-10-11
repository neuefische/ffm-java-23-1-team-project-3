package de.neuefische.backend;

import java.time.ZonedDateTime;

public record Timestamp(ZonedDateTime timestamp) {
	public static Timestamp now() {
		return new Timestamp( ZonedDateTime.now() );
	}
}
