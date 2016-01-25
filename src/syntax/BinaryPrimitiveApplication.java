package syntax;

import token.Symbol;
import token.Token;

public class BinaryPrimitiveApplication implements Expression, Token {
	public String operator;
	public Expression argument1, argument2;

	public BinaryPrimitiveApplication(String op, Token a1, Token a2) {
		operator = op;
		argument1 = (Expression) a1;
		argument2 = (Expression) a2;
	}

	@Override
	public Symbol toSymbol() {
		return Symbol.Expression;
	}
	
	@Override
	public String toString() {
		return argument1.toString() + " " + operator + " " + argument2.toString();
	}

	@Override
	public Expression eval(Environment e) {
		Expression a = argument1.eval(e);
		Expression b = argument2.eval(e);

		switch (operator) {
		case "+":
			if (a instanceof NumberConstant && b instanceof NumberConstant) {
				return new NumberConstant(((NumberConstant) a).value + ((NumberConstant) b).value);
			}
			else if (a instanceof StringConstant && b instanceof StringConstant) {
				return new StringConstant(((StringConstant) a).value + ((StringConstant) b).value);
			}
			break;
		case "-":
			if (a instanceof NumberConstant && b instanceof NumberConstant) {
				return new NumberConstant(((NumberConstant) a).value - ((NumberConstant) b).value);
			}
			break;
		case "*":
			if (a instanceof NumberConstant && b instanceof NumberConstant) {
				return new NumberConstant(((NumberConstant) a).value * ((NumberConstant) b).value);
			}
			break;
		case "/":
			if (a instanceof NumberConstant && b instanceof NumberConstant) {
				return new NumberConstant(((NumberConstant) a).value / ((NumberConstant) b).value);
			}
			break;
		case "&":
			if (a instanceof BooleanConstant && b instanceof BooleanConstant) {
				return new BooleanConstant(((BooleanConstant) a).value && ((BooleanConstant) b).value);
			}
			break;
		case "|":
			if (a instanceof BooleanConstant && b instanceof BooleanConstant) {
				return new BooleanConstant(((BooleanConstant) a).value || ((BooleanConstant) b).value);
			}
			break;
		case ">":
			if (a instanceof NumberConstant && b instanceof NumberConstant) {
				return new BooleanConstant(((NumberConstant) a).value > ((NumberConstant) b).value);
			}
			break;
		case "<":
			if (a instanceof NumberConstant && b instanceof NumberConstant) {
				return new BooleanConstant(((NumberConstant) a).value < ((NumberConstant) b).value);
			}
			break;
		case "<=":
			if (a instanceof NumberConstant && b instanceof NumberConstant) {
				return new BooleanConstant(((NumberConstant) a).value <= ((NumberConstant) b).value);
			}
			break;
		case ">=":
			if (a instanceof NumberConstant && b instanceof NumberConstant) {
				return new BooleanConstant(((NumberConstant) a).value >= ((NumberConstant) b).value);
			}
			break;
		case "==":
		case "=":
			if (a instanceof NumberConstant && b instanceof NumberConstant) {
				return new BooleanConstant(((NumberConstant) a).value == ((NumberConstant) b).value);
			}
			else if (a instanceof BooleanConstant && b instanceof BooleanConstant) {
				return new BooleanConstant(((BooleanConstant) a).value == ((BooleanConstant) b).value);
			}
			else if (a instanceof StringConstant && b instanceof StringConstant) {
				return new BooleanConstant(((StringConstant) a).value.equals(((StringConstant) b).value));
			}
			else {
				return new BooleanConstant(a.getClass().equals(b.getClass()));
			}
		case ":":
			return new PairConstant(a, b);
		case "@":
			if (a instanceof PairConstant && b instanceof PairConstant) {
				boolean error = false;
				PairConstant current = (PairConstant) a;
				while (!(current.tail instanceof EmptyList)) {
					if (current.tail instanceof PairConstant) {
						current = (PairConstant) current.tail;
					}
					else {
						error = true;
						break;
					}
				}
				if (!error) {
					current.tail = b;
					return a;
				}
			}
			break;
		default:
			break;
		}
		throw new IllegalArgumentException("Invalid application of operator " + operator + " on types " + a.getClass().getSimpleName() + " and " + b.getClass().getSimpleName());
	}
	
	@Override
	public String toJSON() {
		return "{\"name\": \"" + operator + "\", children: [" + argument1.toJSON() + ", " + argument2.toJSON() + "]}";
	}
}
