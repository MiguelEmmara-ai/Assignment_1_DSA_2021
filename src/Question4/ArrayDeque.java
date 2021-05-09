package Question4;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <h1>ArrayDeque.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
public class ArrayDeque<E> implements DequeADT<E> {
    private static final int MIN_INITIAL_CAPACITY = 8;
    private int front;
    private int rear;
    private int num_elements;
    private Object[] elements;

    public ArrayDeque() {
        elements = new Object[MIN_INITIAL_CAPACITY];
        front = -1;
        rear = -1;
        num_elements = 0;
    }

    public ArrayDeque(int initial_capacity) {
        elements = new Object[initial_capacity];
        front = -1;
        rear = -1;
        num_elements = 0;
    }

    // ----- Override ----- //
    // Add element to the rear
    @Override
    public void enqueueRear(E element) {
        if (isEmpty()) //list is empty
        {
            // Point front and rear to current location
            front = 0;
            rear = 0;
        } else {
            if (elements.length == num_elements) //list is full, expand it
            {
                rear = elements.length; //pointing rear to next location after expand
                doubleCapacity();
                front = 0; //point front to current location after expand
            } else
                rear = (rear + elements.length + 1) % elements.length; //increase rear by 1 and wraps around the array
        }

        elements[rear] = element;
        num_elements++;
    }

    // Delete First Element
    @Override
    @SuppressWarnings("unchecked")
    public E dequeueFront() {
        if (isEmpty())
            throw new NoSuchElementException("List Is Empty");

        E result = (E) elements[front];
        // If only one element in list.
        if (front == rear) {
            clear();

        } else {
            elements[front] = null;
            front = (front + elements.length + 1) % elements.length; //increase front by one and wraps around the array
            num_elements--;
        }
        return result;
    }

    // Get first element
    @Override
    @SuppressWarnings("unchecked")
    public E first() {
        E result = (E) elements[front];
        if (isEmpty())
            throw new NoSuchElementException("List is empty");

        return result;
    }

    // Add element to the front
    @Override
    public void enqueueFront(E element) {
        if (element == null)
            throw new NullPointerException();
        elements[front = (front - 1) & (elements.length - 1)] = element;
        if (front == rear)
            doubleCapacity();
        num_elements++;
    }

    // Delete Last Element
    @Override
    @SuppressWarnings("unchecked")
    public E dequeueRear() {
        if (isEmpty())
            throw new NoSuchElementException("List Is Empty");

        E element = (E) elements[rear];

        if (front == rear) //only one element in list.
            clear();
        else {
            elements[rear] = null;
            rear = (rear + elements.length - 1) % elements.length; //decrease rear by one and wraps around the array
            num_elements--;
        }

        return element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E last() {
        E result = (E) elements[rear];
        if (isEmpty())
            throw new NoSuchElementException("List is empty");

        return result;
    }

    @Override
    public boolean isEmpty() {
        return num_elements == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(front);
    }

    // Delete all elements
    @Override
    public void clear() {
        elements = new Object[MIN_INITIAL_CAPACITY];
        front = -1;
        rear = -1;
        num_elements = 0;
    }

    @Override
    public int size() {
        return num_elements;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");

        Iterator<E> it = iterator();
        while (it.hasNext()) {
            s.append(it.next().toString());
            if (it.hasNext())
                s.append(", ");
        }

        return s + "]";
    }
    // ----- Override ----- //

    // ----- Helper ----- //
    @SuppressWarnings("unchecked")
    private void doubleCapacity() {
        E[] largerArray = (E[]) (new Object[elements.length * 2]);
        int index = 0;
        Iterator<E> it = iterator();
        while (it.hasNext())
            largerArray[index++] = it.next();

        elements = largerArray;
    }

    private class ArrayIterator<thing> implements Iterator<thing> {
        private int start;
        private int count;

        public ArrayIterator(int front) {
            start = front;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            if (start == -1) //if start is -1 means list is empty
                return false;

            //if array[start] is null then increase start by one so null values are skipped
            while (elements[start] == null && count != num_elements)
                start = (start + elements.length + 1) % elements.length;

            return count != num_elements;
        }

        @Override
        @SuppressWarnings("unchecked")
        public thing next() {
            thing element = (thing) elements[start];
            count++;
            start = (start + elements.length + 1) % elements.length;
            return element;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }
    }
    // ----- Helper ----- //
}
