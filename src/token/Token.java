package token;


public interface Token {
	public Symbol toSymbol();
}

//class Number implements Token {
//	int value;
//	public Number(int value) {
//		this.value = value;
//	}
//	@Override
//	public Symbol toSymbol() {
//		return value == 1 ? Symbol.ONE : Symbol.ZERO;
//	}
//	@Override
//	public String toString() {
//		return "Number [value=" + value + "]";
//	}
//}
