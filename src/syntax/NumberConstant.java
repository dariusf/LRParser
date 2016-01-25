package syntax;

import token.Symbol;
import token.Token;

public class NumberConstant implements Expression, Token {
	private static final double EPSILON = 0.00001;
	double value;

	public NumberConstant(double value) {
		this.value = value;
	}

	@Override
	public Symbol toSymbol() {
		return Symbol.NUMBER;
	}
	
	@Override
	public String toString() {
		if (Math.abs(Math.floor(value) - value) < EPSILON) {
			return Integer.toString((int) value);
		}
		else {
			return Double.toString(value);
		}
	}

	@Override
	public Expression eval(Environment e) {
		return this;
	}

	@Override
	public String toJSON() {
		return "{\"name\": \"" + toString() + "\"}";
	}
}
