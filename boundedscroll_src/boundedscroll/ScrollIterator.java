package boundedscroll;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * This Scroll iterator implements the List Iterator. Use this when you want
 * to iterate through a scroll.
 *
 * @param <E>
 * @author Seth Brown 18 Feb 2022
 */
public class ScrollIterator<E> implements ListIterator<E> {

    Scroll<E> scroll;

    /**
     * Scroll Iterator Constructor.
     *
     * @param scroll the scroll that will be iterated over.
     */
    public ScrollIterator(Scroll<E> scroll) {
        this.scroll = scroll;
    }

    @Override
    public boolean hasNext() {
        return scroll.rightLength() != 0;
    }

    @Override
    public E next() {
        if (!hasNext()) throw new NoSuchElementException("No more elements");
        E next = scroll.getNext();
        scroll.advance();
        return next;
    }

    @Override
    public boolean hasPrevious() {
        return scroll.leftLength() != 0;
    }

    @Override
    public E previous() {
        if (!hasPrevious()) throw new NoSuchElementException("No more elements");
        E previous = scroll.getPrevious();
        return previous;
    }

    @Override
    public int nextIndex() {
        return scroll.leftLength() + 1;
    }

    @Override
    public int previousIndex() {
        return scroll.leftLength() - 1;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void set(E e) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void add(E e) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
