package boundedscroll;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is a List Scroll that extends Abstract Scroll using a List data structure.
 * Use this class when you want to use the scroll data structure with a list implementation.
 *
 * @param <E>
 * @author Seth Brown 18 Feb 2022
 */
public class ListScroll<E> extends AbstractScroll<E> {

    private List<E> elements;
    private int pos;

    /**
     * Constructor for a List Scroll object used to set the capacity and initialize the scroll.
     *
     * @param max amount of elements allowed in the scroll.
     * @throws IllegalArgumentException if capacity is less than zero
     */
    public ListScroll(int max) {
        super(max);
        elements = new ArrayList<>();
        pos = 0;
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
        elements.add(pos, elem);
    }

    /**
     * Delete and return the element to the right of the cursor.
     *
     * @return the element to the right of the cursor
     * @throws IllegalStateException if to the right of the cursor is empty
     */
    @Override
    public E delete() throws IllegalStateException {
        if (rightLength() == 0) {
            throw new IllegalStateException("To the right of the cursor is empty");
        }
        return elements.remove(pos);
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
        pos++;
    }

    /**
     * Moves the cursor one element to the left.
     *
     * @throws IllegalStateException if to the left of the cursor is empty
     */
    @Override
    public void retreat() throws IllegalStateException {
        if (leftLength() == 0) {
            throw new IllegalStateException("To the left of the cursor is empty");
        }
        pos--;
    }

    /**
     * Moves the cursor to the beginning of the scroll.
     */
    @Override
    public void reset() {
        pos = 0;
    }

    /**
     * Move the cursor of the scroll to the end of the scroll.
     */
    @Override
    public void advanceToEnd() {
        pos = elements.size();
    }

    /**
     * Get the quantity of elements left of the cursor.
     *
     * @return the total elements left of the cursor
     */
    @Override
    public int leftLength() {
        return pos;
    }

    /**
     * Get the quantity of elements right of the cursor.
     *
     * @return the total elements left of the cursor
     */
    @Override
    public int rightLength() {
        return elements.size() - pos;
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
        return new ListScroll<>(capacity());
    }
}
