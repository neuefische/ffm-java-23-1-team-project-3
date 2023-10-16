package de.neuefische.backend.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@GetMapping("me")
	public UserInfos getMe() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("Authentication: "+authentication);

//		Object details = authentication.getDetails();
//		Object credentials = authentication.getCredentials();
		Object principal = authentication.getPrincipal();

//		if (details!=null) System.out.println("   Details: "+details.getClass()+" -> "+details);
//		if (credentials!=null) System.out.println("   Credentials: "+credentials.getClass()+" -> "+credentials);
		if (principal!=null) System.out.println("   principal: "+principal.getClass()+" -> "+principal);

		if (principal instanceof DefaultOAuth2User user) {
			System.out.println("login: "+user.getAttribute("login"));
			System.out.println("name: "+user.getAttribute("name"));
			System.out.println("location: "+user.getAttribute("location"));
			System.out.println("url: "+user.getAttribute("url"));
			System.out.println("avatar_url: "+user.getAttribute("avatar_url"));
			return new UserInfos(
					Objects.toString( user.getAttribute("login"), null ),
					Objects.toString( user.getAttribute("name"), null ),
					Objects.toString( user.getAttribute("location"), null ),
					Objects.toString( user.getAttribute("url"), null ),
					Objects.toString( user.getAttribute("avatar_url"), null )
			);
		}

		return new UserInfos( authentication.getName(), null, null,null,null);
	}
}