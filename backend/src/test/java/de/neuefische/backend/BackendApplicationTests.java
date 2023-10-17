package de.neuefische.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BackendApplicationTests {

	@MockBean
	ClientRegistrationRepository clientRegistrationRepository;

    @Test
    void contextLoads() {
	    assertTrue(true);
    }

}
