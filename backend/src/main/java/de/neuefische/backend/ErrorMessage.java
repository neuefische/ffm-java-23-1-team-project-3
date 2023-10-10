package de.neuefische.backend;

import java.time.ZonedDateTime;

public record ErrorMessage(String error, ZonedDateTime timestamp) {
    public ErrorMessage(String error) {
        this(error, ZonedDateTime.now());
    }
}
