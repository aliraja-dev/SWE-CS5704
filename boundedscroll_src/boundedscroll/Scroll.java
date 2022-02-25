package boundedscroll;

import java.util.ListIterator;

// TODO Add exceptions to signatures if both can be thrown, prioritize IllegalArgumentException

/**
 * This the Scroll interface used to define the abstract implementations and exceptions.
 * A scroll is a data structure similar to a list in that it stores elements in a sequence. However, a scroll
 * keeps track of a position in the sequence that we will call the "cursor".
 * A scroll can be depicted using two side-by-side sequences - a left sequence
 * and a right sequenceThe position between the left sequence and the right sequence is the cursor position.
 * The cursor position can be at the beginning of the scroll, at the end of a scroll,
 * or between to elements in the scroll. We say that the elements to the left of the cursor are before the cursor,
 * while the elements to its right are after the cursor
 *
 * @param <E>
 */
public interface Scroll<E> extends Iterable<E> {

    /**
     * Adds an element to the right of the cursor position.
     *
     * @param elem the element to be added to this scroll
     * @throws IllegalArgumentException if the specified element is null
     * @throws IllegalStateException    if the scroll is full and insert is called
     */
    void insert(E elem) throws IllegalArgumentException, IllegalStateException;

    /**
     * Delete and return the element to the right of the cursor.
     *
     * @return the element to the right of the cursor
     * @throws IllegalStateException if to the right of the cursor is empty
     */
    E delete() throws IllegalStateException;

    /**
     * Advance the cursor one element to the right.
     *
     * @throws IllegalStateException if to the right of the cursor is empty
     */
    void advance() throws IllegalStateException;

    /**
     * Moves the cursor one element to the left.
     *
     * @throws IllegalStateException if the right of the cursor is empty
     */
    void retreat() throws IllegalStateException;

    /**
     * Moves the cursor to the beginning of the scroll.
     */
    public void reset();

    /**
     * Move the cursor of the scroll to the end of the scroll.
     */
    void advanceToEnd();

    /**
     * Swap the right part of the current scroll with the right part of the scroll passed in as
     * a parameter.
     *
     * @param that the scroll that will the current scroll will replace the currents right side of the scroll.
     * @throws IllegalArgumentException if when replaced, it exceeds the capacity for the scroll.
     */
    void swapRights(Scroll<E> that) throws IllegalArgumentException;

    /**
     * Get the quantity of elements left of the cursor.
     *
     * @return the total elements left of the cursor
     */
    int leftLength();

    /**
     * Get the quantity of elements right of the cursor.
     *
     * @return the total elements left of the cursor
     */
    int rightLength();

    /**
     * Creates a new instance of a scroll. The new scroll has the same concrete
     * type that "this" scroll does, and it also has the same capacity. However, the new scroll
     * is empty – it does not contain any elements.
     *
     * @return a new empty scroll that has the same concrete type and capacity that the calling type does.
     */
    Scroll<E> newInstance();

    /**
     * Get the maximum capacity of the scroll.
     *
     * @return the maximum amount elements allowed in the scroll
     */
    int capacity();

    /**
     * Iterate through the scroll starting at the current cursor's position
     * iterating forward until the end of the scroll. This will ignore all elements
     * to the left (before) the cursor. After the iteration, the cursor is at the end of the scroll.
     *
     * @return the list with the cursor at the end
     */
    ListIterator<E> listIterator();

    /**
     * Gets the element to the right of the cursor and does not alter the scroll or
     * does not change the position of the cursor.
     *
     * @return the element to the right of the cursor.
     * @throws IllegalStateException if there is no element to the right of the cursor.
     */
    E getNext() throws IllegalStateException;

    /**
     * Gets the element to the left of the cursor and does not alter the scroll or
     * does not change the position of the cursor.
     *
     * @return the element to the left of the cursor.
     * @throws IllegalStateException if there is no element to the left of the cursor.
     */
    E getPrevious() throws IllegalStateException;

    /**
     * Replace the element to the right of the cursor and return the original element.
     * Does not alter the position of the cursor.
     *
     * @param element the element to replace the current element
     * @return return the original element that was replaced.
     * @throws IllegalStateException if there is no element to the right of the cursor.
     */
    E replace(E element) throws IllegalStateException;

    /**
     * Add the elements from the passed in scroll to the right of the cursor in the
     * current scroll. The passed in scroll will now be empty. The cursor of the passed
     * in scroll must be at the beginning of the scroll.
     *
     * @param that the scroll to remove the elements from.
     * @throws IllegalStateException if adding the new scroll would exceed scroll capacity
     */
    void splice(Scroll<E> that) throws IllegalStateException;

    /**
     * Reverses the scroll by taking all the elements to the right of the scroll and
     * moving them to the left of the scroll in order. The cursor must start at the beginning
     * of the scroll and when the reverse is complete, the cursor will be at the end of the scroll.
     *
     * @throws IllegalStateException if the scroll is null
     */
    void reverse() throws IllegalStateException;

    /**
     * Checks for equality between the scroll and the passed in object.
     *
     * @param o the object to be compared to the scroll.
     * @return true if the object and scroll are equal.
     * @throws IllegalArgumentException if the object is not a scroll.
     */
    boolean equals(Object o) throws IllegalArgumentException;

    /**
     * Get the hashcode of the scroll.
     *
     * @return the hashcode of the scroll.
     */
    int hashCode();

    /**
     * This is the toString of the scroll interface represented as the example
     * "[A, B, C][D, E, F]:10" where the cursor is in between c and d, and 10 is the
     * capacity.
     *
     * @return the string of the scroll.
     */
    String toString();
}
