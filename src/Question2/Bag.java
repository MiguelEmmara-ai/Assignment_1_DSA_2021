package Question2;

import java.util.Iterator;

/**
 * <h1>Bag.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
public interface Bag<E> {
    boolean add(E item);

    E grab();

    boolean remove(E item);

    int size();

    int capacityRemaining();

    boolean isFull();

    boolean isEmpty();

    void clear();

    Iterator<E> iterator();

    E[] toArray();
}
