package boundedstack;

/**
 * A stack is a first-in-last-out data structure. This stack is bounded,
 * therefore it should have a single-argument constructor that takes an
 * integer that represents the maximum number of elements this stack can hold.
 * @param <E>
 */
public interface Stack<E> {

    /**
     * Adds the specified element to the top of this stack
     * @param element the element to be added to this stack
     * @throws IllegalStateException if this stack is full
     */
    void push(E element);

    /**
     * Removes and returns an element from the top of this stack
     * @return the element removed from this stack
     * @throws IllegalStateException if this stack is empty
     */
    E pop();

    /**
     * Returns the depth of this stack
     * @return the number of elements currently in this stack
     */
    int depth();

    /**
     * Returns the capacity of this stack
     * @return the maximum number of elements this stack can hold
     */
    int capacity();
}