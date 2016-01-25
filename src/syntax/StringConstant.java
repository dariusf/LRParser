package syntax;

import token.Symbol;
import token.Token;

public class StringConstant implements Token, Expression {
	String value;

	public StringConstant(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
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
		return Symbol.STRING;
	}
}
