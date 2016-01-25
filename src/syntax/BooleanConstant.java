package syntax;

import token.Symbol;
import token.Token;

public class BooleanConstant implements Token, Expression {
	boolean value;

	public BooleanConstant(boolean value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return Boolean.toString(value);
	}

	@Override
	public Expression eval(Environment e) {
		return this;
	}
	
	@Override
	public String toJSON() {
		return "{\"name\": \"" + toString() + "\"}";
	}

	@Override
	public Symbol toSymbol() {
		return Symbol.BOOLEAN;
	}
}
