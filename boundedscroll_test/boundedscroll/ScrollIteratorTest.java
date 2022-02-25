package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ScrollIteratorTest {

    Scroll<String> ab_cd_4;
    ScrollIterator<String> scrollIterator;


    @Before
    public void setUp() throws Exception {
        ab_cd_4 = new ListScroll<>(4);
        scrollIterator = new ScrollIterator<>(ab_cd_4);


    }

    @Test
    public void hasNext() {
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        assertTrue(scrollIterator.hasNext());
        ab_cd_4.advance();
        ab_cd_4.advance();
        ab_cd_4.advance();
        assertFalse(scrollIterator.hasNext());
    }

    @Test
    public void next() {
        ab_cd_4.insert("A");
        ab_cd_4.insert("B");
        ab_cd_4.insert("C");
        assertEquals(3, ab_cd_4.rightLength());
        assertEquals("C", scrollIterator.next());
        assertEquals("B", scrollIterator.next());
        assertEquals("A", scrollIterator.next());
        try {
            scrollIterator.next();
        } catch (NoSuchElementException noSuchElementException) {
            assertEquals("No more elements", noSuchElementException.getMessage());
        }
    }

    @Test
    public void hasPrevious() {
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        assertFalse(scrollIterator.hasPrevious());
        ab_cd_4.advanceToEnd();
        assertTrue(scrollIterator.hasPrevious());
    }

    @Test
    public void previous() {
        ab_cd_4.insert("A");
        ab_cd_4.insert("B");
        ab_cd_4.insert("C");
        ab_cd_4.advanceToEnd();
        assertEquals(3, ab_cd_4.leftLength());
        assertEquals("A", scrollIterator.previous());
        assertEquals("B", scrollIterator.previous());
        assertEquals("C", scrollIterator.previous());
        try {
            scrollIterator.previous();
        } catch (NoSuchElementException noSuchElementException) {
            assertEquals("No more elements", noSuchElementException.getMessage());
        }
    }

    @Test
    public void nextIndex() {
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.advanceToEnd();
        assertEquals(3, scrollIterator.nextIndex());
    }

    @Test
    public void previousIndex() {
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.advanceToEnd();
        assertEquals(1, scrollIterator.previousIndex());
    }

    @Test
    public void remove() {
        try {
            scrollIterator.remove();
        } catch (UnsupportedOperationException exception) {
            assertEquals("Not implemented", exception.getMessage());
        }
    }

    @Test
    public void set() {

        try {
            scrollIterator.set("A");
        } catch (UnsupportedOperationException exception) {
            assertEquals("Not implemented", exception.getMessage());
        }
    }

    @Test
    public void add() {
        try {
            scrollIterator.add("A");
        } catch (UnsupportedOperationException exception) {
            assertEquals("Not implemented", exception.getMessage());
        }
    }
}