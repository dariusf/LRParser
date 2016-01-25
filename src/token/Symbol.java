package token;

public enum Symbol {

	// Keywords
	
	LET("LET", false),
	IN("IN", false),
	IF("IF", false),
	THEN("THEN", false),
	ELSE("ELSE", false),
	FUNCTION("FUNCTION", false),
	ARROW("ARROW", false),
	
	// Unary operators
	
	MINUS("MINUS", false),
	NOT("NOT", false),
	HEAD("HEAD", false),
	TAIL("TAIL", false),
	
	// Binary operators
	
	TIMES("TIMES", false),
	DIVIDE("DIVIDE", false),
	PLUS("PLUS", false),
	EQUAL("EQUAL", false),
//	DIV("DIV", false),
	LESS("LESS", false),
	GREATER("GREATER", false),
	LEQ("LEQ", false),
	GEQ("GEQ", false),
	AND("AND", false),
	OR("OR", false),
	EQUALEQUAL("EQUALEQUAL", false),
	APPEND("APPEND", false),
	CONS("CONS", false),

	Application("Application", true),
	Expression("Expression", true),
	Factor("Factor", true),
	FunctionExpr("FunctionExpr", true),
	GroupedExpression("GroupedExpression", true),
	IfExpr("IfExpr", true),
	LetExpr("LetExpr", true),
	PairConstant("PairConstant", true),
	AndOperand("AndOperand", true),
	AppendOperand("AppendOperand", true),
	ComparisonOperand("ComparisonOperand", true),
	ConsOperand("ConsOperand", true),
	OrOperand("OrOperand", true),
	Term("Term", true),
	UnaryOperand("UnaryOperand", true),
	Value("Value", true),
	
	IdentifierList("IdentifierList", true),
	ExpressionList("ExpressionList", true),

	// Values
	
	ID("id", false), // TODO rename
	BOOLEAN("boolean", false),
	EMPTYLIST("emptylist", false),
	NUMBER("number", false),
	STRING("string", false),

	// Other nonterminals
	
	LBRACKET("(", false),
	RBRACKET(")", false),
	LSQBR("[", false),
	RSQBR("]", false),
	COMMA("COMMA", false),
	
	// Special ones
	START("S", true),
	MARKER(".", false),
	EOF("$", false),
	EPSILON("epsilon", false);

	// Delete when done
//	X("X", true),
//	Y("Y", true),
//	Z("Z", true),
//	a("a", false),
//	b("b", false),
//	c("c", false),
//	E("E", true),
//	F("F", true),
//	T("T", true),

	String name;
	boolean nonterminal;
	
	private Symbol(String s, boolean nonterminal) {
		name = s;
		this.nonterminal = nonterminal;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean isNonterminal() {
		return nonterminal;
	}

	public boolean isTerminal() {
		return !nonterminal;
	}
}
