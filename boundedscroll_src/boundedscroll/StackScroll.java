package boundedscroll;

import java.util.Stack;

/**
 * This class is a Stack Scroll that extends Abstract Scroll using a stack data structure.
 * Use this class when you want to use the scroll data structure with a stack implementation.
 *
 * @param <E>
 * @author Seth Brown 18
 */
public class StackScroll<E> extends AbstractScroll<E> {

    Stack<E> left, right;

    /**
     * Constructor for a Stack Scroll object used to set the capacity and initialize the scroll.
     *
     * @param max amount of elements allowed in the scroll.
     * @throws IllegalArgumentException if capacity is less than zero
     */
    public StackScroll(int max) {
        super(max);
        left = new Stack<>();
        right = new Stack<>();
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
        if (left.size() + right.size() == capacity()) throw new IllegalStateException("Scroll is full");
        right.push(elem);
    }

    /**
     * Delete and return the element to the right of the cursor.
     *
     * @return the element to the right of the cursor
     * @throws IllegalStateException if to the right of the cursor is empty
     */
    @Override
    public E delete() throws IllegalStateException {
        if (right.isEmpty()) throw new IllegalStateException("To the right of the cursor is empty");
        return right.pop();
    }

    /**
     * Advance the cursor one element to the right.
     *
     * @throws IllegalStateException if to the right of the cursor is empty
     */
    @Override
    public void advance() throws IllegalStateException {
        if (right.isEmpty()) throw new IllegalStateException("To the right of the cursor is empty");
        left.push(right.pop());
    }

    /**
     * Moves the cursor one element to the left.
     *
     * @throws IllegalStateException if to the left of the cursor is empty
     */
    @Override
    public void retreat() throws IllegalStateException {
        if (left.isEmpty()) throw new IllegalStateException("To the left of the cursor is empty");
        right.push(left.pop());
    }

    /**
     * Moves the cursor to the beginning of the scroll.
     */
    @Override
    public void reset() {
        while (leftLength() != 0) {
            retreat();
        }
    }

    /**
     * Move the cursor of the scroll to the end of the scroll.
     */
    @Override
    public void advanceToEnd() {
        while (rightLength() != 0) {
            advance();
        }
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
        if (that instanceof StackScroll<?>) {
            StackScroll<E> stackScrollThat = (StackScroll<E>) that;
            Stack<E> temp = this.right;
            this.right = stackScrollThat.right;
            stackScrollThat.right = temp;
        } else {
            super.swapRights(that);
        }
    }

    /**
     * Get the quantity of elements left of the cursor.
     *
     * @return the total elements left of the cursor
     */
    @Override
    public int leftLength() {
        return left.size();
    }

    /**
     * Get the quantity of elements right of the cursor.
     *
     * @return the total elements left of the cursor
     */
    @Override
    public int rightLength() {
        return right.size();
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
        return new StackScroll<>(capacity());
    }
}
