package com.cbeardsmore.scart.rmp;

import com.cbeardsmore.scart.rmp.persistence.Bookmark;
import com.cbeardsmore.scart.rmp.utils.PostgresDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookmarkTest {

    private static Bookmark bookmark;

    @BeforeAll
    static void beforeAll() throws IOException {
        bookmark = new Bookmark(PostgresDatabase.provide());
    }

    @Test
    void whenGetBookmarkWithNoEventsShouldReturnZero() {
        assertEquals(5, bookmark.get());
    }

    @Test
    void whenPutBookmarkShouldUpdateBookmarkPosition() {
        bookmark.put(5);
        assertEquals(5, bookmark.get());
    }
}