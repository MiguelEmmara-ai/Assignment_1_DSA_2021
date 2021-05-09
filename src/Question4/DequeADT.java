package Question4;

import java.util.Iterator;

/**
 * <h1>DequeADT.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
interface DequeADT<E> {
    void enqueueRear(E element);

    E dequeueFront();

    E first();

    void enqueueFront(E element);

    E dequeueRear();

    E last();

    boolean isEmpty();

    Iterator<E> iterator();

    void clear();

    int size();
}