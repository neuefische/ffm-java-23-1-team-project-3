package de.neuefische.backend.security;

public record UserInfos(
		String login,
		String name,
		String location,
		String url,
		String avatar_url
) {
}
