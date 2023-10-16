package de.neuefische.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
	// for IntelliJ

	private static Book createBook() {
		return new Book("i", "t", "a", "d", "p", "i", "u", false);
	}

	@Test void testWithId         () { assertEquals(new Book(" ", "t", "a", "d", "p", "i", "u", false), createBook().withId         (" ")); }
	@Test void testWithTitle      () { assertEquals(new Book("i", " ", "a", "d", "p", "i", "u", false), createBook().withTitle      (" ")); }
	@Test void testWithAuthor     () { assertEquals(new Book("i", "t", " ", "d", "p", "i", "u", false), createBook().withAuthor     (" ")); }
	@Test void testWithDescription() { assertEquals(new Book("i", "t", "a", " ", "p", "i", "u", false), createBook().withDescription(" ")); }
	@Test void testWithPublisher  () { assertEquals(new Book("i", "t", "a", "d", " ", "i", "u", false), createBook().withPublisher  (" ")); }
	@Test void testWithIsbn       () { assertEquals(new Book("i", "t", "a", "d", "p", " ", "u", false), createBook().withIsbn       (" ")); }
	@Test void testWithCoverUrl   () { assertEquals(new Book("i", "t", "a", "d", "p", "i", " ", false), createBook().withCoverUrl   (" ")); }
}