package Question6;

import java.util.Stack;

/**
 * <h1>ExpressionTreeBuilder.java</h1>
 *
 * @author Miguel Emmara - 18022146
 */
public class ExpressionTreeBuilder {

    public static ExpNode buildExpressionTree(String[] postfixStrings) {
        Stack<ExpNode> stack = new Stack<>();
        OperatorNode operatorNode;
        ExpNode parent = null;

        for (String postfixString : postfixStrings) {
            if (isOperand(postfixString)) {
                stack.push(new OperandNode(postfixString));
            } else if (isOperator(postfixString)) {
                operatorNode = new OperatorNode(postfixString);
                parent = operatorNode;

                parent.rightChild = stack.pop();
                if (!postfixString.equalsIgnoreCase("~")) {
                    parent.leftChild = stack.pop();
                }

                stack.push(parent);
            }
        }

        return parent;
    }

    public static int countNodes(ExpNode node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + countNodes(node.leftChild) + countNodes(node.rightChild);
        }
    }

    public static String toInfixString(ExpNode node) {
        if (node == null) {
            return "";
        } else if ((node.leftChild == null && node.rightChild == null) || node.symbol.equalsIgnoreCase("~")) {
            return toInfixString(node.leftChild) + node + toInfixString(node.rightChild);
        } else {
            return "(" + toInfixString(node.leftChild) + node + toInfixString(node.rightChild) + ")";
        }
    }

    public static boolean isOperand(String value) {
        if (value == null) {
            return false;
        } else {
            try {
                Double.parseDouble(value);

            } catch (NumberFormatException nfe) {
                return false;
            }
        }

        return true;
    }

    public static boolean isOperator(String symbol) {
        return     symbol.equalsIgnoreCase("*")
                || symbol.equalsIgnoreCase("/")
                || symbol.equalsIgnoreCase("-")
                || symbol.equalsIgnoreCase("+")
                || symbol.equalsIgnoreCase("~");
    }
}
