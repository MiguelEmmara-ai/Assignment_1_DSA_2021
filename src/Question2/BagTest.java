package Question2;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * <h1>BagTest.java</h1>
 * Bag Test class to test two different implementation
 * (Underlying Array or Doubly Link list)
 * User Driven Choice
 *
 * @author Created by Miguel Emmara - 18022146
 */
public class BagTest {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int userAnswer;
        boolean stop = true;
        System.out.println("Would you Like to use ArrayBag or LinkedBag?");
        do {
            try {
                mainMenu();
                userAnswer = scanner.nextInt();
                switch (userAnswer) {
                    case 1:
                        chooseDataBag("\nArrayBag", "arraybag");
                        stop = false;
                        break;

                    case 2:
                        chooseDataBag("\nLinked Bag", "linkedbag");
                        stop = false;
                        break;

                    case 3:
                        System.out.println("\nGoodbye!");
                        stop = false;
                        break;

                    default:
                        throw new IllegalArgumentException("Please Choose Between 1 - 3");
                }
            } catch (IndexOutOfBoundsException | InputMismatchException | IllegalArgumentException e) {
                System.out.println("Please Enter The Correct Options, 1 - 3");
                scanner.nextLine();
            }
        } while (stop);
    }

    private static void chooseDataBag(String s, String bagList) {
        boolean stop = false;
        int userAnswer;
        System.out.println(s);
        System.out.println("--------");

        System.out.println("Would you like to use fixed capacity or enter yourself?");

        do {
            try {
                System.out.println("\n\t1. Fixed Capacity (10)");
                System.out.println("\t2. Enter Myself");
                System.out.print("\nAnswer: ");
                userAnswer = scanner.nextInt();
                scanner.nextLine();

                switch (userAnswer) {
                    case 1:
                        stop = theBag(true, bagList);
                        break;

                    case 2:
                        stop = theBag(false, bagList);
                        break;

                    default:
                        System.out.println("Please Enter The Correct Options, 1 - 2");
                }
            } catch (IndexOutOfBoundsException | InputMismatchException | IllegalArgumentException e) {
                System.out.println("Please Enter The Correct Options, 1 - 2");
                scanner.nextLine();
            }
        } while (!stop);
    }

    private static boolean theBag(boolean isFixed, String bagDS) {
        int maxSize = 0;
        int counter = 1;
        boolean stop = false;
        int userItemCounter;
        Object object = new Object();

        if (bagDS.equalsIgnoreCase("arraybag")) {
            if (isFixed) {
                System.out.println("\nFixed Capacity Chosen!");
                object = new ArrayBag<String>();
            } else {

                do {
                    try {
                        System.out.print("\nEnter your bag capacity: ");
                        maxSize = BagTest.scanner.nextInt();

                        stop = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Please Input A Valid Number!");
                        BagTest.scanner.nextLine();
                    }
                } while (!stop);

                object = new ArrayBag<String>(maxSize);
            }

        } else if (bagDS.equalsIgnoreCase("linkedbag")) {
            if (isFixed) {
                System.out.println("\nFixed Capacity Chosen!");
                object = new LinkedBag<String>();
            } else {
                do {
                    try {
                        System.out.print("\nEnter your bag capacity: ");
                        maxSize = BagTest.scanner.nextInt();

                        stop = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Please Input A Valid Number!");
                        BagTest.scanner.nextLine();
                    }
                } while (!stop);

                object = new LinkedBag<String>(maxSize);
            }
        }

        stop = false;
        do {
            try {
                System.out.print("\nHow many items would you like to add to the bag?: ");
                userItemCounter = BagTest.scanner.nextInt();
                BagTest.scanner.nextLine();

                if (isFixed) {
                    counter = simulation(counter, userItemCounter, object);
                    stop = true;
                } else {
                    if (userItemCounter <= maxSize) {
                        counter = simulation(counter, userItemCounter, object);
                        stop = true;
                    } else {
                        System.out.println("Item Must Be Less Than Or Equal To " + maxSize);
                    }
                }

            } catch (InputMismatchException e) {
                System.out.println("Please Input A Valid Number!");
                BagTest.scanner.nextLine();
            }
        } while (!stop);

        return true;
    }

    @SuppressWarnings("unchecked")
    private static int simulation(int counter, int userItemCounter, Object object) {
        String item;
        // Implementing Adding Method
        System.out.println("\nAdding item into the bag");
        for (int i = 0; i < userItemCounter; i++) {
            System.out.print("Item " + counter + ": ");
            item = BagTest.scanner.nextLine();
            ((Bag<String>) object).add(item);
            counter++;
        }

        System.out.println("\nBag = " + object);
        System.out.println("Bag is full?: " + ((Bag<String>) object).isFull());
        System.out.println();

        // Implementing Grab Method
        System.out.println("Grabbing an item from the bag");
        System.out.println("Item [" + ((Bag<String>) object).grab() + "] grabbed!");

        // Implementing Remove Method
        System.out.print("\nItem to remove: ");
        item = BagTest.scanner.nextLine();
        System.out.println("\nRemoving item [" + item + "] from the bag");
        if (((Bag<String>) object).remove(item)) {
            System.out.println("Item [" + item + "] removed!");
        } else {
            System.out.println("Error, item [" + item + "] is not exist in the bag!");
        }


        // Implementing Capacity Remaining
        System.out.println("\nCurrent item in the bag: " + object);
        System.out.println("Capacity Remaining: " + ((Bag<String>) object).capacityRemaining());
        System.out.println("Bag is Empty?: " + ((Bag<String>) object).isEmpty());

        // Implementing Clear Method
        System.out.println("\nClearing all items in the bag");
        ((Bag<String>) object).clear();
        System.out.println("Bag is clear!");
        System.out.println("\nCurrent item in the bag: " + object);
        System.out.println("Bag is Empty?: " + ((Bag<String>) object).isEmpty());
        return counter;
    }

    private static void mainMenu() {
        System.out.println("\n\t1. ArrayBag");
        System.out.println("\t2. LinkedBag");
        System.out.println("\t3. Exit");
        System.out.print("\nAnswer: ");
    }
}