package syntax;

import token.Symbol;
import token.Token;;

public class UnaryPrimitiveApplication implements Expression, Token {

    public String operator;
    public Expression argument;

    public UnaryPrimitiveApplication(String op, Token a) {
        operator = op;
        argument = (Expression) a;
    }
    
    @Override
    public String toString() {
    	return operator + argument.toString();
    }

	@Override
	public Expression eval(Environment e) {
		Expression a = argument.eval(e);
		
		switch (operator) {
		case "!":
			if (a instanceof BooleanConstant) {
				return new BooleanConstant(!((BooleanConstant) a).value);
			}
			break;
		case "-":
			if (a instanceof NumberConstant) {
				return new NumberConstant(-((NumberConstant) a).value);
			}
			break;
		case "#":
			if (a instanceof PairConstant) {
				return ((PairConstant) a).head;
			}
			break;
		case "~":
			if (a instanceof PairConstant) {
				return ((PairConstant) a).tail;
			}
			break;
		default:
			break;
		}
		throw new IllegalArgumentException("Invalid application of operator " + operator + " on type " + a.getClass().getSimpleName());
	}

	@Override
	public String toJSON() {
		return "{\"name\": \"" + operator + "\", \"children\": [" + argument.toJSON() + "]}";
	}

	@Override
	public Symbol toSymbol() {
		return Symbol.Expression;
	}
}
