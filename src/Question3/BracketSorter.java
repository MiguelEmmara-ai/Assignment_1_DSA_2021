package Question3;

import java.util.Scanner;
import java.util.Stack;

/**
 * <h1>Bracket.java</h1>
 * @author Created by Miguel Emmara - 18022146
 *
 * Reference: Data Structures and Algorithms in Java, 6th Edition by Michael T. Goodrich (Author).
 *
 * Sample Are :
 * "{((2 x 5)+(3*-2 + 5))}",                -> Evaluate successfully
 * "{ (2 x 5)+(3*-2 + 5))}",                -> Did not match up!
 * "List<List>",                            -> Evaluate successfully
 * "List<List ",                            -> Did not match up!
 * "{(<>){}{…}(e(e)e){hello}}",
 * "{(< eeeek>>){}{…} e(e)e){hello} "       -> Evaluate successfully
 */
public class BracketSorter {
    public static boolean isMatched(String expression) {
        final String opening = "({[<";
        final String closing = ")}]>";

        Stack<Character> buffer = new Stack<>();
        for (char c : expression.toCharArray()) {
            if (opening.indexOf(c) != -1) {
                buffer.push(c);
            } else if (closing.indexOf(c) != -1) {
                if (buffer.isEmpty()) {
                    return false;
                }
                if (closing.indexOf(c) != opening.indexOf(buffer.pop())) {
                    return false;
                }
            }
        }
        return buffer.isEmpty();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("Enter string containing delimiters");
            System.out.println("Type 'x' to exit the application");
            System.out.print("> ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("x"))
                break;

            if (isMatched(input)) {
                System.out.println("\nEvaluate successfully\n");
            } else {
                System.out.println("\nDid not match up!\n");
            }
        }
    }
}