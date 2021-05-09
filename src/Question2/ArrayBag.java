package Question2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <h1>ArrayBag.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
public class ArrayBag<E> implements Bag<E> {
    private static final int FIXED_CAPACITY = 10;
    private final Object[] items;
    private int size;

    public ArrayBag() {
        this.items = new Object[FIXED_CAPACITY];
        this.size = 0;
    }

    public ArrayBag(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Error, Max Size Must Be Greater Than 0");
        }
        this.items = new Object[maxSize];
        this.size = 0;
    }

    // ----- Override ----- //
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");

        for (int i = 0; i < this.size; i++) {
            str.append(this.items[i]);
            if (i != this.size - 1) {
                str.append(", ");
            }
        }

        str.append("]");
        return str.toString();
    }

    @Override
    public boolean add(E item) {
        boolean result = true;
        if (isFull()) {
            System.out.println("Bag is full! Cannot add item [" + item + "]");
            result = false;
        } else {
            // Assertion: result is true here
            items[size] = item;
            size++;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E grab() {
        if (this.size == 0) {
            throw new IllegalStateException("the bag is empty");
        }

        int theItem = (int) (Math.random() * this.size);
        return (E) this.items[theItem];
    }

    @Override
    public boolean remove(E item) {
        // search for the index of the element o in the set
        int index = 0;
        boolean found = false;

        for (int i = 0; i < size && !found; i++) {
            if ((items[i] == null && item == null) || (items[i] != null && items[i].equals(item))) {
                index = i;
                found = true;
            }
        }

        // replace the element at index by last element
        if (found) {
            items[index] = items[size - 1];
            items[size - 1] = null; //removes reference to element
            size--;
        }

        return found;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacityRemaining() {
        return this.items.length - size;
    }

    @Override
    public boolean isFull() {
        return this.items.length == this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }

        size = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>((E[]) items, size);
    }

    @Override
    public E[] toArray() {
        @SuppressWarnings("unchecked")
        E[] array = (E[]) new Object[this.size];

        System.arraycopy(this.items, 0, array, 0, size);

        return array;
    }
    // ----- Override ----- //

    // inner class which represents an iterator for an array
    private static class ArrayIterator<E> implements Iterator<E> {
        private final int size;
        private final E[] items;
        private int nextIndex;

        public ArrayIterator(E[] elements, int numElements) {
            if (numElements > elements.length)
                numElements = elements.length;
            this.size = numElements;
            this.items = elements;
            nextIndex = 0; // start with index of first element in array
        }

        // returns whether there is still another element
        public boolean hasNext() {
            return (nextIndex < size);
        }

        // returns the next element or throws a NoSuchElementException
        // it there are no further elements
        public E next() throws NoSuchElementException {
            if (!hasNext())
                throw new NoSuchElementException();
            return items[nextIndex++];
        }

        // remove method not supported by this iterator
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }
}
