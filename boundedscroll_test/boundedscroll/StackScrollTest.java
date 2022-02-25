package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StackScrollTest {

    Scroll<String> ab_cd_6;
    Scroll<String> ab_cd_4;
    Scroll<String> ab_cd_4_2;


    @Before
    public void setUp() throws Exception {
        ab_cd_6 = new StackScroll<>(6);
        ab_cd_4 = new StackScroll<>(4);
        ab_cd_4_2 = new StackScroll<>(4);

    }

    @Test
    public void constructorTest() {
        try {
            ab_cd_6 = new StackScroll<>(-5);
        } catch (IllegalArgumentException illegalArgumentException) {
            assertEquals("Input must be greater than 0", illegalArgumentException.getMessage());
        }
    }

    @Test
    public void initSetup() {
        ab_cd_6.insert("D");
        ab_cd_6.insert("C");
        ab_cd_6.insert("B");
        ab_cd_6.insert("A");
        ab_cd_6.reset();
        ab_cd_6.advance();
        ab_cd_6.advance();

        assertEquals(2, ab_cd_6.rightLength());
        assertEquals(2, ab_cd_6.leftLength());
        assertEquals(6, ab_cd_6.capacity());
        assertEquals("C", ab_cd_6.getNext());
    }

    @Test
    public void insert() {
        ab_cd_6.insert("D");
        assertEquals(1, ab_cd_6.rightLength());
        try {
            ab_cd_6.insert(null);
            fail();
        } catch (IllegalArgumentException illegalArgumentException) {
            assertEquals("Element cannot be null", illegalArgumentException.getMessage());
        }
        try {
            ab_cd_6.insert("A");
            ab_cd_6.insert("A");
            ab_cd_6.insert("A");
            ab_cd_6.insert("A");
            ab_cd_6.insert("6");
            ab_cd_6.insert("7");
            fail();
        } catch (IllegalStateException illegalStateException) {
            assertEquals("Scroll is full", illegalStateException.getMessage());
        }
        ab_cd_6.reset();
    }

    @Test
    public void retreat() {
        ab_cd_6.insert("A");
        ab_cd_6.insert("A");
        ab_cd_6.insert("A");
        ab_cd_6.advance();
        ab_cd_6.advance();
        ab_cd_6.retreat();
        assertEquals(2, ab_cd_6.rightLength());
        try {
            ab_cd_6.retreat();
            ab_cd_6.retreat();
        } catch (IllegalStateException illegalStateException) {
            assertEquals("To the left of the cursor is empty", illegalStateException.getMessage());
        }
    }

    @Test
    public void advance() {
        ab_cd_6.insert("A");
        ab_cd_6.insert("A");
        ab_cd_6.insert("A");
        ab_cd_6.advance();
        assertEquals(1, ab_cd_6.leftLength());
        try {
            ab_cd_6.advance();
            ab_cd_6.advance();
            ab_cd_6.advance();
            fail();
        } catch (IllegalStateException illegalStateException) {
            assertEquals("To the right of the cursor is empty", illegalStateException.getMessage());
        }
    }

    @Test
    public void delete() {
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.advance();
        ab_cd_4.advance();
        ab_cd_4.advance();
        ab_cd_4.advance();
        try {
            ab_cd_4.delete();
        } catch (IllegalStateException illegalStateException) {
            assertEquals("To the right of the cursor is empty", illegalStateException.getMessage());
        }
        ab_cd_4.retreat();
        ab_cd_4.delete();
        assertEquals(0, ab_cd_4.rightLength());
        assertEquals(3, ab_cd_4.leftLength());
    }

    @Test
    public void reset() {
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.advance();
        ab_cd_4.advance();
        assertEquals(2, ab_cd_4.rightLength());
        assertEquals(2, ab_cd_4.leftLength());
        ab_cd_4.reset();
        assertEquals(4, ab_cd_4.rightLength());
    }

    @Test
    public void advanceToEnd() {
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.advance();
        ab_cd_4.advance();
        assertEquals(2, ab_cd_4.rightLength());
        assertEquals(2, ab_cd_4.leftLength());
        ab_cd_4.advanceToEnd();
        assertEquals(4, ab_cd_4.leftLength());
    }

    @Test
    public void leftLength() {
        assertEquals(0, ab_cd_4.leftLength());
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.advance();
        ab_cd_4.advance();
        assertEquals(2, ab_cd_4.leftLength());
    }

    @Test
    public void rightLength() {
        assertEquals(0, ab_cd_4.rightLength());
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        assertEquals(2, ab_cd_4.rightLength());
    }

    @Test
    public void newInstance() {
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        assertEquals(4, ab_cd_4.rightLength());
        assertEquals(0, ab_cd_4.newInstance().rightLength());
    }

    @Test
    public void swapRights() {
        ab_cd_4.insert("A");
        ab_cd_4.insert("A");
        ab_cd_4_2.insert("1");
        ab_cd_4_2.insert("1");
        ab_cd_4_2.insert("1");
        assertEquals(2, ab_cd_4.rightLength());
        assertEquals(3, ab_cd_4_2.rightLength());
        ab_cd_4_2.swapRights(ab_cd_4);
        assertEquals(3, ab_cd_4.rightLength());
        assertEquals(2, ab_cd_4_2.rightLength());
        ab_cd_4.insert("BB");
        ab_cd_4_2.advance();
        try {
            ab_cd_4_2.swapRights(ab_cd_4);
        } catch (IllegalStateException e) {
            assertEquals("Exceeds capacity of calling object", e.getMessage());
        }
    }

}