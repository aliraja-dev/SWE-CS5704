package boundedscroll;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * This class is a Linked Scroll that extends Abstract Scroll using a node a
 * nd creating a doubling linked list scroll.
 *
 * @param <E>
 * @author Seth Brown 18 Feb 2022
 */
public class LinkedScroll<E> extends AbstractScroll<E> {
    Node guard;
    Node cursor;

    class Node {
        E contents;
        Node next;
        Node previous;

        public Node(E contents) {
            this.contents = contents;
        }
    }

    /**
     * Constructor for a Linked Scroll object used to set the capacity and initialize the scroll.
     *
     * @param max amount of elements allowed in the scroll.
     * @throws IllegalArgumentException if capacity is less than zero
     */
    public LinkedScroll(int max) {
        super(max);
        guard = new Node(null);
        guard.next = guard;
        guard.previous = guard;
        cursor = new Node(null);
        cursor.next = guard;
        cursor.previous = guard;
    }

    /**
     * Adds an element to the right of the cursor position.
     *
     * @param elem the element to be added to this scroll
     * @throws IllegalArgumentException if the specified element is null
     * @throws IllegalStateException    if the scroll is full and insert is called
     */
    @Override
    public void insert(E elem) throws IllegalArgumentException, IllegalStateException {
        if (elem == null) throw new IllegalArgumentException("Element cannot be null");
        if (rightLength() + leftLength() == capacity()) throw new IllegalStateException("Scroll is full");
        Node newNode = new Node((E) elem);
        newNode.next = cursor.next;
        newNode.previous = cursor.previous;

        cursor.previous.next = newNode;
        cursor.next.previous = newNode;
        cursor.next = newNode;
    }

    /**
     * Delete and return the element to the right of the cursor.
     *
     * @return the element to the right of the cursor
     * @throws IllegalStateException if there is no element to the right
     */
    @Override
    public E delete() throws IllegalStateException {
        if (rightLength() == 0) throw new IllegalStateException("To the right of the cursor is empty");
        Node result = cursor.next;
        result.previous.next = result.next;
        result.next.previous = result.previous;

        cursor.next = cursor.next.next;
        cursor.previous = cursor.previous.next;

        return result.contents;
    }

    /**
     * Advance the cursor one element to the right.
     *
     * @throws IllegalStateException if to the right of the cursor is empty
     */
    @Override
    public void advance() throws IllegalStateException {
        if (rightLength() == 0) {
            throw new IllegalStateException("To the right of the cursor is empty");
        }
        cursor.previous = cursor.next;
        cursor.next = cursor.next.next;
    }

    /**
     * Moves the cursor one element to the left.
     *
     * @throws IllegalStateException if to the right of the cursor is empty
     */
    @Override
    public void retreat() throws IllegalStateException {
        if (leftLength() == 0) {
            throw new IllegalStateException("To the left of the cursor is empty");
        }
        cursor.next = cursor.previous;
        cursor.previous = cursor.previous.previous;
    }

    /**
     * Moves the cursor to the beginning of the scroll.
     */
    @Override
    public void reset() {
        cursor.next = guard.next;
        cursor.previous = guard;
    }

    /**
     * Move the cursor of the scroll to the end of the scroll.
     */
    @Override
    public void advanceToEnd() {
        cursor.next = guard;
        cursor.previous = guard.previous;
    }

    /**
     * Get the quantity of elements left of the cursor.
     *
     * @return the total elements left of the cursor
     */
    @Override
    public int leftLength() {
        Node temp = cursor;
        int count = 0;
        while (temp.previous != guard) {
            count++;
            temp = temp.previous;
        }
        return count;
    }

    /**
     * Get the quantity of elements right of the cursor.
     *
     * @return the total elements left of the cursor
     */
    @Override
    public int rightLength() {
        Node temp = cursor;
        int count = 0;
        while (temp.next != guard) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    /**
     * Creates a new instance of a scroll. The new scroll has the same concrete
     * type that "this" scroll does, and it also has the same capacity. However, the new scroll
     * is empty – it does not contain any elements.
     *
     * @return a new empty scroll that has the same concrete type and capacity that the calling type does.
     */
    @Override
    public Scroll<E> newInstance() {
        return new LinkedScroll<>(capacity());
    }
}
