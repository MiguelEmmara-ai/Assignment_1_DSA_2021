package Question6;

/**
 * <h1>ExpNode.java</h1>
 * Miguel Emmara - 18022146
 *
 * @author Seth
 */
public abstract class ExpNode {
    public ExpNode leftChild;
    public ExpNode rightChild;

    protected String symbol;

    public ExpNode(String value) {
        this.symbol = value;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public abstract double evaluate() throws ArithmeticException;
}