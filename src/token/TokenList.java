package token;
import java.util.ArrayList;


/**
 * A container class for tokens on the stack.
 */
public class TokenList implements Token {
	
	public ArrayList<Token> tokens = new ArrayList<>();
	
	public TokenList() {
	}
	
	@Override
	public Symbol toSymbol() {
		// should never be called
		assert false;
		return null;
	}

	@Override
	public String toString() {
		return "TokenList [tokens=" + tokens + "]";
	}
}
