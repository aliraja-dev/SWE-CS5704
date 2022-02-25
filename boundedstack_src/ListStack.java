package boundedstack;

import java.util.ArrayList;
import java.util.List;

public class ListStack<E> implements Stack<E> {
    private List<E> contents;
    private final int capacity;

    public ListStack(int capacity) {
        if (capacity < 1) throw new IllegalArgumentException();
        contents = new ArrayList<>();
        this.capacity = capacity;
    }

    @Override
    public void push(E element) {
        if (depth() == capacity()) throw new IllegalStateException();
        contents.add(element);
    }

    @Override
    public E pop() {
        if (depth() < 1) throw new IllegalStateException();
        return contents.remove(depth() - 1);
    }

    @Override
    public int depth() {
        return contents.size();
    }

    @Override
    public int capacity() {
        return capacity;
    }
}
