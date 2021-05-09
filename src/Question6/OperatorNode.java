package Question6;

/**
 * <h1>OperatorNode.java</h1>
 *
 * @author Created by Miguel Emmara - 18022146
 */
public class OperatorNode extends ExpNode {
    private String operator;

    public OperatorNode(String value) {
        super(value);

        try {
            setOperator(value);

            if (!(getOperator().equalsIgnoreCase("*")
                    || getOperator().equalsIgnoreCase("/")
                    || getOperator().equalsIgnoreCase("-")
                    || getOperator().equalsIgnoreCase("+")
                    || getOperator().equalsIgnoreCase("~"))) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double evaluate() throws ArithmeticException {
        switch (getOperator()) {
            case "*":
                return leftChild.evaluate() * rightChild.evaluate();
            case "/":
                return leftChild.evaluate() / rightChild.evaluate();
            case "-":
                return leftChild.evaluate() - rightChild.evaluate();
            case "+":
                return leftChild.evaluate() + rightChild.evaluate();
            case "~":
                if (rightChild != null) {
                    if (rightChild.evaluate() == 0D)
                        throw new ArithmeticException();
                    return 1 / rightChild.evaluate();
                }
            default:
                throw new ArithmeticException();
        }
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
