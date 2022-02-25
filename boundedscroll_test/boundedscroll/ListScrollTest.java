package boundedscroll;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListScrollTest {
    Scroll<String> ab_cd_6;
    Scroll<String> ab_cd_4;
    Scroll<String> ab_cd_4_2;

    Scroll<String> stackScroll5;
    Scroll<String> stackScroll;
    Scroll<String> stackScroll2;
    Scroll<String> listScroll5;
    Scroll<String> listScroll;
    Scroll<String> listScroll2Double;
    Scroll<String> listScroll2;
    Scroll<String> listScrollNull;
    StringBuilder sb;


    @Before
    public void setUp() throws Exception {
        ab_cd_6 = new ListScroll<>(6);
        ab_cd_4 = new ListScroll<>(4);
        ab_cd_4_2 = new ListScroll<>(4);
        listScroll = new ListScroll<>(4);
        listScroll2 = new ListScroll<>(2);
        listScroll2Double = new ListScroll<>(2);
        stackScroll5 = new StackScroll<>(5);
        stackScroll = new StackScroll<>(5);
        stackScroll2 = new StackScroll<>(2);
        listScroll5 = new ListScroll<>(5);
        sb = new StringBuilder();

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
    public void insert() {
        ab_cd_6.insert("D");
        assertEquals(1, ab_cd_6.rightLength());
        listScroll.insert("a");
        assertEquals(1, listScroll.rightLength());
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
            ab_cd_6.insert("5");
            ab_cd_6.insert("6");
            ab_cd_6.insert("7");
            ab_cd_6.insert("8");
        } catch (IllegalStateException illegalStateException) {
            assertEquals("Scroll is full", illegalStateException.getMessage());
        }
        assertEquals(0, ab_cd_6.leftLength());
        ab_cd_6.reset();
        assertEquals("6", ab_cd_6.getNext());
        ab_cd_6.advance();
        assertEquals("5", ab_cd_6.getNext());

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
        stackScroll2.insert("b");
        listScroll5.insert("2");
        listScroll5.insert("1");
        listScroll5.swapRights(stackScroll2);
        assertEquals(1, listScroll5.rightLength());
        listScroll5.insert("a");
        assertEquals(2, stackScroll2.rightLength());
        stackScroll2.swapRights(listScroll5);
        assertEquals(2, listScroll5.rightLength());
        listScroll5.insert("3");
        listScroll5.reset();
        System.out.println(listScroll5.toString());
        stackScroll5.insert("ASSDF");
        stackScroll5.swapRights(listScroll5);
        assertEquals(3, stackScroll5.rightLength());
        assertEquals(1,listScroll5.rightLength());
    }

    @Test
    public void capacity() {
        assertEquals(4, listScroll.capacity());
        assertEquals(5, stackScroll.capacity());
    }

    @Test
    public void getNext() {
        try {
            listScroll.getNext();
        } catch (IllegalStateException e) {
            assertEquals("No elements to the right of the cursor", e.getMessage());
        }
        listScroll.insert("5");
        assertEquals("5", listScroll.getNext());
    }

    @Test
    public void getPrevious() {
        try {
            listScroll.getPrevious();
        } catch (IllegalStateException e) {
            assertEquals("No elements to the left of the cursor", e.getMessage());
        }
        listScroll.insert("5");
        listScroll.advanceToEnd();
        assertEquals("5", listScroll.getPrevious());
    }

    @Test
    public void replace() {
        try {
            listScroll.replace("5");
        } catch (IllegalStateException e) {
            assertEquals("No elements to the right of the cursor", e.getMessage());
        }
        listScroll.insert("4");
        assertEquals("4", listScroll.getNext());
        listScroll.replace("6");
        assertEquals("6", listScroll.getNext());
    }

    @Test
    public void splice() {
        listScroll2.insert("A");
        listScroll2.insert("A");
        listScroll.insert("a");
        try {
            listScroll2.splice(listScrollNull);
        } catch (IllegalArgumentException e) {
            assertEquals("Input is null or cursor not at beginning", e.getMessage());
        }
        try {
            listScroll2.advance();
            listScroll.splice(listScroll2);
        } catch (IllegalArgumentException e) {
            assertEquals("Input is null or cursor not at beginning", e.getMessage());
        }
        try {
            listScroll2.splice(listScroll);
        } catch (IllegalStateException e) {
            assertEquals("Exceeds capacity of calling object", e.getMessage());
        }
        listScroll2.reset();
        listScroll.reset();
        assertEquals(1, listScroll.rightLength());
        assertEquals(2, listScroll2.rightLength());
        listScroll.splice(listScroll2);
        assertEquals(1, listScroll.rightLength());
        assertEquals(2, listScroll.leftLength());
        assertEquals(0, listScroll2.rightLength());
        assertEquals(0, listScroll2.leftLength());
        listScroll.reset();
        assertEquals("A", listScroll.getNext());
        listScroll.advance();
        assertEquals("A", listScroll.getNext());
        listScroll.advance();
        assertEquals("a", listScroll.getNext());

    }

    @Test
    public void reverse() {
        listScroll.insert("C");
        listScroll.advance();
        try {
            listScroll.reverse();
        } catch (IllegalStateException e) {
            assertEquals("Cursor not at beginning", e.getMessage());
        }
        listScroll.reset();
        listScroll.insert("B");
        listScroll.insert("A");
        assertEquals(3, listScroll.rightLength());
        System.out.println(listScroll.toString());
        listScroll.reverse();
        assertEquals(3, listScroll.leftLength());
        System.out.println(listScroll.toString());



    }

    @Test
    public void iterator() {
        listScroll.insert("1");
        listScroll.insert("2");
        listScroll.insert("3");
        listScroll.insert("4");
        listScroll.advance();
        listScroll.advance();
        for (String s : listScroll) {
            sb.append(s);
        }
        assertEquals("21", sb.toString());
        assertEquals(4, listScroll.leftLength());


    }

    @Test
    public void listIterator() {
        listScroll.insert("1");
        listScroll.insert("2");
        listScroll.insert("3");
        listScroll.insert("4");
        listScroll.advance();
        listScroll.advance();
        for (String s : listScroll) {
            sb.append(s);
        }
        assertEquals("21", sb.toString());
        assertEquals(4, listScroll.leftLength());

    }

    @Test
    public void testEquals() {
        listScroll2.insert("a");
        listScroll2.insert("b");
        listScroll2Double.insert("a");
        listScroll2Double.insert("b");
        assertTrue(listScroll2.equals(listScroll2Double));
        listScroll2.advance();
        assertFalse(listScroll2.equals(listScroll2Double));
        listScroll2.retreat();
        listScroll2.delete();
        listScroll2.insert("o");
        assertFalse(listScroll2.equals(listScroll2Double));
        listScroll2.delete();
        assertFalse(listScroll2.equals(listScroll2Double));
        assertFalse(listScroll2.equals(listScroll));

        stackScroll5.insert("b");
        stackScroll5.insert("a");
        listScroll5.insert("b");
        listScroll5.insert("a");
        assertTrue(stackScroll5.equals(listScroll5));

        listScroll5.advanceToEnd();
        stackScroll5.advanceToEnd();
        assertTrue(stackScroll5.equals(listScroll5));
    }

    @Test
    public void testToString() {
        listScroll.insert("d");
        listScroll.insert("c");
        listScroll.insert("b");
        assertEquals(3, listScroll.rightLength());
        listScroll.insert("a");
        assertEquals("[][a, b, c, d]:4", listScroll.toString());
        assertEquals(4, listScroll.rightLength());
        listScroll.advance();
        listScroll.advance();
        assertEquals("[a, b][c, d]:4", listScroll.toString());
        assertEquals("[][]:2", listScroll2.toString());
        listScroll.advance();
        listScroll.advance();
        assertEquals("[a, b, c, d][]:4", listScroll.toString());

        stackScroll.insert("5");
        stackScroll.insert("4");
        stackScroll.insert("3");
        stackScroll.insert("2");
        stackScroll.insert("1");
        assertEquals("[][1, 2, 3, 4, 5]:5", stackScroll.toString());

    }

    @Test
    public void testHashCode() {
        listScroll2.insert("a");
        listScroll2Double.insert("a");
        stackScroll2.insert("a");
        assertEquals(1, stackScroll2.rightLength());
        assertEquals(1, listScroll2.rightLength());
        assertEquals(1, listScroll2Double.rightLength());
        assertTrue(listScroll2.hashCode() == listScroll2Double.hashCode());
        assertTrue(stackScroll2.hashCode() == listScroll2Double.hashCode());
        assertTrue(listScroll2.hashCode() == stackScroll2.hashCode());
        assertFalse(listScroll2.hashCode() == listScroll.hashCode());
        listScroll2.insert("a");
        listScroll2Double.insert("b");
        assertFalse(listScroll2.hashCode() == listScroll2Double.hashCode());
    }

}