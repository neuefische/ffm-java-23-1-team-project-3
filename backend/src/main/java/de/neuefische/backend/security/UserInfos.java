package de.neuefische.backend.security;

public record UserInfos(
		boolean isAuthenticated,
		String id,
		String login,
		String name,
		String location,
		String url,
		String avatar_url
) {
}
