package Question6;

/**
 * <h1>OperandNode.java</h1>
 *
 * @author Miguel Emmara - 18022146
 */
public class OperandNode extends ExpNode {
    private double double_value = 0.0;

    public OperandNode(String value) {
        super(value);

        try {
            setDouble_value(Double.parseDouble(value));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double evaluate() throws ArithmeticException {
        return getDouble_value();
    }

    public double getDouble_value() {
        return double_value;
    }

    public void setDouble_value(double double_value) {
        this.double_value = double_value;
    }
}
