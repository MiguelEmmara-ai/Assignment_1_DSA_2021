package Question4;

/**
 * <h1>ArrayDequeTest.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
public class ArrayDequeTest {
    public static void main(String[] args) {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();

        // Enqueue
        System.out.println("Adding 1 2 3 to the front of the queue");
        arrayDeque.enqueueFront(1);
        arrayDeque.enqueueFront(2);
        arrayDeque.enqueueFront(3);

        System.out.println(arrayDeque);

        System.out.println("\nAdding 4 5 6 to the rear of the queue");
        arrayDeque.enqueueRear(4);
        arrayDeque.enqueueRear(5);
        arrayDeque.enqueueRear(6);

        System.out.println(arrayDeque);

        // Get first and last element
        System.out.println("\nFirst Element: " + arrayDeque.first());
        System.out.println("last Element: " + arrayDeque.last());

        // Dequeue
        System.out.println("\nDequeue Front");
        arrayDeque.dequeueFront();

        System.out.println(arrayDeque);

        System.out.println("\nDequeue Rear");
        arrayDeque.dequeueRear();

        System.out.println(arrayDeque);

        // Rest of the method
        System.out.println("\nIs Empty: " + arrayDeque.isEmpty());
        System.out.println("Size: " + arrayDeque.size());

        System.out.println("\nClearing Dequeue");
        arrayDeque.clear();
        System.out.println(arrayDeque);
        System.out.println("Size: " + arrayDeque.size());
        System.out.println("Is Empty: " + arrayDeque.isEmpty());
    }
}
