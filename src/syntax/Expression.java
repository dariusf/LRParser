package syntax;

public interface Expression {
	public String toString();
	public String toJSON();
	public Expression eval(Environment e);
}
