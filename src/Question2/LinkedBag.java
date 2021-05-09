package Question2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <h1>LinkedBag.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
public class LinkedBag<E> implements Bag<E> {
    private static final int INITIAL_CAPACITY = 10;
    private final int maxSize;
    private Node<E> first;
    private Node<E> last;
    private int size;

    public LinkedBag() {
        this.first = null;
        this.size = 0;
        this.maxSize = INITIAL_CAPACITY;
    }

    public LinkedBag(int maxSize) {
        this.first = null;
        this.size = 0;
        this.maxSize = maxSize;
    }

    // ----- Overrides ----- //
    @Override
    public boolean add(E item) {
        final Node<E> last_node = last;
        final Node<E> newNode = new Node<>(last_node, item, null);
        last = newNode;
        if (last_node == null)
            first = newNode;
        else
            last_node.next = newNode;
        size++;
        return true;
    }

    @Override
    public E grab() {
        if (this.size == 0) {
            throw new IllegalStateException("the bag is empty");
        }

        int theItem = (int) (Math.random() * this.size);
        return node(theItem).item;
    }

    @Override
    public boolean remove(E item) {
        if (item == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (item.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacityRemaining() {
        return (maxSize - size);
    }

    @Override
    public boolean isFull() {
        return (size == maxSize);
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        while (!isEmpty())
            remove();
        first = last = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        Node<E> current = first;

        for (int i = 0; i < this.size; i++) {
            str.append(current.item);
            if (i != this.size - 1) {
                str.append(", ");
            }
            current = current.next;
        }

        str.append("]");
        return str.toString();
    }

    @Override
    public E[] toArray() {
        @SuppressWarnings("unchecked")
        E[] result = (E[]) new Object[size];
        int i = 0;
        for (Node<E> x = first; x != null; x = x.next)
            result[i++] = x.item;
        return result;
    }
    // ----- Overrides ----- //

    // ----- Helper -----
    public void remove() {
        final Node<E> first_node = first;
        if (first_node == null)
            throw new NoSuchElementException();
        unlinkFirst(first_node);
    }

    Node<E> node(int index) {
        Node<E> x;
        if (index < (size >> 1)) {
            x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
        } else {
            x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
        }
        return x;
    }

    void unlink(Node<E> x) {
        // assert x != null;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
    }

    private void unlinkFirst(Node<E> f) {
        // assert f == first && f != null;
        final Node<E> next = f.next;
        f.item = null;
        f.next = null; // help GC
        first = next;
        if (next == null)
            last = null;
        else
            next.prev = null;
        size--;
    }
    // ----- Helper ----- //

    // Inner Class
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
