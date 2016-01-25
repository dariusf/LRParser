package syntax;


import java.util.ArrayList;
import java.util.Vector;


import token.Symbol;
import token.Token;
import token.TokenList;

public class PairConstant implements Expression, Token {
	
	public Expression head;
	public Expression tail;
	
	public PairConstant() {}
	
	public PairConstant(Expression  h, Expression t) {
		this.head = h;
		this.tail = t;
	}

	public PairConstant(Token h, Token t) {
		this.head = (Expression) h;
		this.tail = (Expression) t;
	}
	
	public PairConstant(Token lst) {
		assert lst instanceof TokenList;

		ArrayList<Token> elements = ((TokenList) lst).tokens;
		
		PairConstant p = this;
		for (int i=0; i<elements.size()-1; ++i) {
			p.head = (Expression) elements.get(i);
			p.tail = new PairConstant();
			p = (PairConstant) p.tail;
		}
		p.head = (Expression) elements.get(elements.size()-1);
		p.tail = new EmptyList();
	}

	@Override
	public Symbol toSymbol() {
		return Symbol.PairConstant;
	}

	public PairConstant(Vector<Expression> elements) {
		PairConstant p = this;
		for (int i=0; i<elements.size()-1; ++i) {
			p.head = elements.get(i);
			p.tail = new PairConstant();
			p = (PairConstant) p.tail;
		}
		p.head = elements.get(elements.size()-1);
		p.tail = new EmptyList();
	}
	
	@Override
	public String toString() {
		return "(" + head.toString() + ", " + tail.toString() + ")";
	}

	@Override
	public Expression eval(Environment e) {
		head = head.eval(e);
		tail = tail.eval(e);
		return this;
	}

	@Override
	public String toJSON() {
		// cons representation
		return "{\"name\": \":\", \"children\": [" + head.toJSON() + ", " + tail.toJSON() + "]}";
		
		// nested pair representation
//		return "{\"name\": \"" + toString() + "\"}";
	}
}
