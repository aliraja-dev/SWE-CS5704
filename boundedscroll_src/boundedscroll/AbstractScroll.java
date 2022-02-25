package boundedscroll;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * Abstract Scroll implementing the Scroll interfaces secondary methods. Handles situations where
 * different scroll types are used with a calling method.
 *
 * @param <E>
 */
public abstract class AbstractScroll<E> implements Scroll<E> {
    private final int capacity;

    /**
     * Constructor for a Scroll object used to set the capacity of the scroll.
     *
     * @param max amount of elements allowed in the scroll.
     * @throws IllegalArgumentException if capacity is less than zero
     */
    public AbstractScroll(int max) throws IllegalArgumentException {
        if (max < 0) throw new IllegalArgumentException("Input must be greater than 0");
        capacity = max;
    }

    /**
     * Swap the right part of the current scroll with the right part of the scroll passed in as
     * a parameter.
     *
     * @param that the scroll that will the current scroll will replace the currents right side of the scroll.
     * @throws IllegalStateException if it exceeds the capacity for the scroll.
     */
    @Override
    public void swapRights(Scroll<E> that) throws IllegalArgumentException, IllegalStateException {
        if (this.leftLength() + that.rightLength() > this.capacity() || that.leftLength() + this.rightLength() > that.capacity())
            throw new IllegalStateException("Exceeds capacity of calling object");
        Scroll<E> temp = this.newInstance();
        int rightLengthThat = that.rightLength();
        int rightLengthThis = this.rightLength();
        for (int i = 0; i < rightLengthThis; i++) {
            this.advanceToEnd();
            temp.insert(this.getPrevious());
            this.delete();
        }
        for (int i = 0; i < rightLengthThat; i++) {
            that.advanceToEnd();
            this.insert(that.getPrevious());
            that.delete();
        }
        for (int i = 0; i < rightLengthThis; i++) {
            temp.advanceToEnd();
            that.insert(temp.getPrevious());
            temp.delete();
        }
        if (rightLengthThat != that.rightLength()) {
            while (rightLengthThat > this.rightLength()) {
                retreat();
            }
        }
        if (rightLengthThis != that.rightLength()) {
            while (rightLengthThis > this.rightLength()) {
                retreat();
            }
        }
    }

    /**
     * Get the maximum capacity of the scroll.
     *
     * @return the maximum amount elements allowed in the scroll
     */
    @Override
    public int capacity() {
        return capacity;
    }


    /**
     * Gets the element to the right of the cursor and does not alter the scroll or
     * does not change the position of the cursor.
     *
     * @return the element to the right of the cursor.
     * @throws IllegalStateException if there is no element to the right of the cursor.
     */
    @Override
    public E getNext() throws IllegalStateException {
        if (rightLength() == 0) throw new IllegalStateException("No elements to the right of the cursor");
        E result = delete();
        insert(result);
        return result;
    }

    /**
     * Gets the element to the left of the cursor and does not alter the scroll or
     * does not change the position of the cursor.
     *
     * @return the element to the left of the cursor.
     * @throws IllegalStateException if there is no element to the left of the cursor.
     */
    @Override
    public E getPrevious() throws IllegalStateException {
        if (leftLength() == 0) throw new IllegalStateException("No elements to the left of the cursor");
        retreat();
        E result = delete();
        insert(result);
        return result;
    }

    /**
     * Replace the element to the right of the cursor and return the original element.
     * Does not alter the position of the cursor.
     *
     * @param element the element to replace the current element
     * @return return the original element that was replaced.
     * @throws IllegalStateException if there is no element to the right of the cursor.
     */
    @Override
    public E replace(E element) throws IllegalStateException {
        if (rightLength() == 0) throw new IllegalStateException("No elements to the right of the cursor");
        delete();
        insert(element);
        return getNext();
    }

    /**
     * Add the elements from the passed in scroll to the right of the cursor in the
     * current scroll. The passed in scroll will now be empty. The cursor of the passed
     * in scroll must be at the beginning of the scroll.
     *
     * @param that the scroll to remove the elements from.
     * @throws IllegalArgumentException if that is null or cursor not at the beginning
     * @throws IllegalStateException    if adding the new scroll would exceed scroll capacity
     */
    @Override
    public void splice(Scroll<E> that) throws IllegalStateException {
        if (that == null || that.leftLength() != 0)
            throw new IllegalArgumentException("Input is null or cursor not at beginning");
        if (this.rightLength() + this.leftLength() + that.rightLength() > this.capacity())
            throw new IllegalStateException("Exceeds capacity of calling object");
        if (that.rightLength() == 0) {
            return;
        }
        this.insert(that.delete());
        this.advance();
        splice(that);
    }


    /**
     * Reverses the scroll by taking all the elements to the right of the scroll and
     * moving them to the left of the scroll in order. The cursor must start at the beginning
     * of the scroll and when the reverse is complete, the cursor will be at the end of the scroll.
     *
     * @throws IllegalStateException if the cursor isnt at the beginning of the scroll
     */
    @Override
    public void reverse() throws IllegalStateException {
        if (leftLength() != 0) throw new IllegalStateException("Cursor not at beginning");
        Scroll<E> temp = this.newInstance();
        Scroll<E> temp2 = this;
        int rightLength = rightLength();
        for (int i = 0; i < rightLength; i++) {
            temp.insert(temp2.getNext());
            temp2.delete();
        }
        this.swapRights(temp);
        advanceToEnd();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<E> iterator() {
        return new ScrollIterator<E>(this);
    }

    /**
     * Iterate through the scroll starting at the current cursor's position
     * iterating forward until the end of the scroll. This will ignore all elements
     * to the left (before) the cursor. After the iteration, the cursor is at the end of the scroll.
     *
     * @return the list with the cursor at the end
     */
    @Override
    public ListIterator<E> listIterator() {
        return new ScrollIterator<E>(this);
    }

    // ----------------------------------
    // object methods
    // ----------------------------------

    @Override
    public boolean equals(Object o) {
        Scroll<?> that = (Scroll<?>) o;
        if ((this.leftLength() != that.leftLength()) || (this.rightLength() != that.rightLength())
                || (this.capacity() != that.capacity())) return false;
        Scroll<?> thisTemp = this;
        Scroll<?> thatTemp = that;
        thisTemp.reset();
        thatTemp.reset();
        for (int i = 0; i < that.capacity(); i++) {
            if (thisTemp.getNext() != thatTemp.getNext()) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int leftLength = leftLength();
        int rightLength = rightLength();
        reset();
        sb.append("[");
        for (int i = 0; i < leftLength; i++) {
            E elem = getNext();
            if (i > 0) sb.append(", ");
            sb.append(elem);
            advance();
        }
        sb.append("]");

        // right
        sb.append("[");
        for (int i = 0; i < rightLength; i++) {
            E elem = getNext();
            if (i > 0) sb.append(", ");
            sb.append(elem);
            advance();
        }
        sb.append("]");
        sb.append(":");
        sb.append(capacity());
        reset();
        while (leftLength != leftLength()) {
            advance();
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int leftLength = leftLength();
        int result = 17;
        reset();
        for (E element : this) {
            result = 31 * result + element.hashCode();
        }
        result = 31 * result + capacity();
        reset();
        while (leftLength < leftLength()) {
            advance();
        }
        return result;
    }
}