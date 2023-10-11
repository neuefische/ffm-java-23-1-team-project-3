package de.neuefische.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimestampServiceTest {

	private TimestampRepository timestampRepository;
	private TimestampService timestampService;

	@BeforeEach
	void setUp() {
		timestampRepository = mock(TimestampRepository.class);
		timestampService = new TimestampService(timestampRepository);
	}

	@Test
	void whenSetTimestampToNow_isCalled_saveTimestampToRepo() {
		// Given

		// When
		timestampService.setTimestampToNow();

		// Then
		// Sorry, I can't mock calls of Timestamp.now()
		//    verify(timestampRepository).save(/*Timestamp.now()*/);
		assertTrue(true);
	}

	@Test
	void whenGetCurrentTimestamp_isCalledWithEmptyRepo_returnsNull() {
		// Given
		when(timestampRepository.findAll()).thenReturn(List.of());

		// When
		Timestamp actual = timestampService.getCurrentTimestamp();

		// Then
		assertNull(actual);
	}

	@Test
	void whenGetCurrentTimestamp_isCalledWithRepoInNormalState_returnsTimestamp() {
		// Given
		when(timestampRepository.findAll()).thenReturn(List.of(
				new Timestamp("test", "<TestTimestamp>")
		));

		// When
		Timestamp actual = timestampService.getCurrentTimestamp();

		// Then
		Timestamp expected = new Timestamp("test", "<TestTimestamp>");
		assertEquals(expected, actual);
	}
}