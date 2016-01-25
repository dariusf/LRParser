package token;


public class Operator implements Token {
	String op;
	public Operator(String op) {
		this.op = op;
	}
	@Override
	public Symbol toSymbol() {
		switch (op) {
		case "+":
			return Symbol.PLUS;
		case "*":
			return Symbol.TIMES;
		case "/":
			return Symbol.DIVIDE;
		case "=":
			return Symbol.EQUAL;
		case "!":
			return Symbol.NOT;
		case "-":
			return Symbol.MINUS;
		case "#":
			return Symbol.HEAD;
		case "~":
			return Symbol.TAIL;
		case "|":
			return Symbol.OR;
		case "&":
			return Symbol.AND;
		case "==":
			return Symbol.EQUALEQUAL;
		case ">":
			return Symbol.GREATER;
		case "<":
			return Symbol.LESS;
		case ">=":
			return Symbol.GEQ;
		case "<=":
			return Symbol.LEQ;
		case "@":
			return Symbol.APPEND;
		case ":":
			return Symbol.CONS;
		}
		assert false;
		return null;
	}
	@Override
	public String toString() {
		return "Operator [op=" + op + "]";
	}
}