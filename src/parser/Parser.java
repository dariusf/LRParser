package parser;

import syntax.*;
import test.Toyscript;
import token.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Parser {
	public static ArrayList<Production> productions = new ArrayList<Production>();

	public static void init() {
		productions.add(new Production(Symbol.Expression, new ArrayList<Symbol>(Arrays.asList(Symbol.PairConstant))));
		productions.add(new Production(Symbol.Expression, new ArrayList<Symbol>(Arrays.asList(Symbol.Application))));
		productions.add(new Production(Symbol.Expression, new ArrayList<Symbol>(Arrays.asList(Symbol.LetExpr))));
		productions.add(new Production(Symbol.Expression, new ArrayList<Symbol>(Arrays.asList(Symbol.IfExpr))));
		productions.add(new Production(Symbol.Expression, new ArrayList<Symbol>(Arrays.asList(Symbol.FunctionExpr))));
		productions.add(new Production(Symbol.Expression, new ArrayList<Symbol>(Arrays.asList(Symbol.OrOperand))));
	
		productions.add(new Production(Symbol.OrOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.OrOperand, Symbol.OR, Symbol.AndOperand))));
		productions.add(new Production(Symbol.OrOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.AndOperand))));
		productions.add(new Production(Symbol.AndOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.AndOperand, Symbol.AND, Symbol.ComparisonOperand))));
		productions.add(new Production(Symbol.AndOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.ComparisonOperand))));
		productions.add(new Production(Symbol.ComparisonOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.ComparisonOperand, Symbol.EQUALEQUAL, Symbol.AppendOperand))));
		productions.add(new Production(Symbol.ComparisonOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.ComparisonOperand, Symbol.EQUAL, Symbol.AppendOperand))));
		productions.add(new Production(Symbol.ComparisonOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.ComparisonOperand, Symbol.GREATER, Symbol.AppendOperand))));
		productions.add(new Production(Symbol.ComparisonOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.ComparisonOperand, Symbol.LESS, Symbol.AppendOperand))));
		productions.add(new Production(Symbol.ComparisonOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.ComparisonOperand, Symbol.GEQ, Symbol.AppendOperand))));
		productions.add(new Production(Symbol.ComparisonOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.ComparisonOperand, Symbol.LEQ, Symbol.AppendOperand))));
		productions.add(new Production(Symbol.ComparisonOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.AppendOperand))));
		productions.add(new Production(Symbol.AppendOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.AppendOperand, Symbol.APPEND, Symbol.ConsOperand))));
		productions.add(new Production(Symbol.AppendOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.ConsOperand))));
		productions.add(new Production(Symbol.ConsOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.ConsOperand, Symbol.CONS, Symbol.Term))));
		productions.add(new Production(Symbol.ConsOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.Term))));
		productions.add(new Production(Symbol.Term, new ArrayList<Symbol>(Arrays.asList(Symbol.Term, Symbol.PLUS, Symbol.Factor))));
		productions.add(new Production(Symbol.Term, new ArrayList<Symbol>(Arrays.asList(Symbol.Term, Symbol.MINUS, Symbol.Factor))));
		productions.add(new Production(Symbol.Term, new ArrayList<Symbol>(Arrays.asList(Symbol.Factor))));
		productions.add(new Production(Symbol.Factor, new ArrayList<Symbol>(Arrays.asList(Symbol.Factor, Symbol.TIMES, Symbol.UnaryOperand))));
		productions.add(new Production(Symbol.Factor, new ArrayList<Symbol>(Arrays.asList(Symbol.Factor, Symbol.DIVIDE, Symbol.UnaryOperand))));
		productions.add(new Production(Symbol.Factor, new ArrayList<Symbol>(Arrays.asList(Symbol.UnaryOperand))));
	
		productions.add(new Production(Symbol.UnaryOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.NOT, Symbol.GroupedExpression))));
		productions.add(new Production(Symbol.UnaryOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.MINUS, Symbol.GroupedExpression))));
		productions.add(new Production(Symbol.UnaryOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.HEAD, Symbol.GroupedExpression))));
		productions.add(new Production(Symbol.UnaryOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.TAIL, Symbol.GroupedExpression))));
		productions.add(new Production(Symbol.UnaryOperand, new ArrayList<Symbol>(Arrays.asList(Symbol.GroupedExpression))));
	
		productions.add(new Production(Symbol.GroupedExpression, new ArrayList<Symbol>(Arrays.asList(Symbol.LBRACKET, Symbol.Expression, Symbol.RBRACKET))));
		productions.add(new Production(Symbol.GroupedExpression, new ArrayList<Symbol>(Arrays.asList(Symbol.Value))));
	
		productions.add(new Production(Symbol.Value, new ArrayList<Symbol>(Arrays.asList(Symbol.ID))));
		productions.add(new Production(Symbol.Value, new ArrayList<Symbol>(Arrays.asList(Symbol.BOOLEAN))));
		productions.add(new Production(Symbol.Value, new ArrayList<Symbol>(Arrays.asList(Symbol.EMPTYLIST))));
		productions.add(new Production(Symbol.Value, new ArrayList<Symbol>(Arrays.asList(Symbol.NUMBER))));
		productions.add(new Production(Symbol.Value, new ArrayList<Symbol>(Arrays.asList(Symbol.STRING))));
	
		productions.add(new Production(Symbol.PairConstant, new ArrayList<Symbol>(Arrays.asList(Symbol.LSQBR, Symbol.ExpressionList, Symbol.RSQBR))));
		productions.add(new Production(Symbol.Application, new ArrayList<Symbol>(Arrays.asList(Symbol.ID, Symbol.LBRACKET, Symbol.ExpressionList, Symbol.RBRACKET))));
		productions.add(new Production(Symbol.Application, new ArrayList<Symbol>(Arrays.asList(Symbol.ID, Symbol.LBRACKET, Symbol.RBRACKET))));
		productions.add(new Production(Symbol.FunctionExpr, new ArrayList<Symbol>(Arrays.asList(Symbol.FUNCTION, Symbol.LBRACKET, Symbol.IdentifierList, Symbol.RBRACKET, Symbol.ARROW, Symbol.Expression))));
		productions.add(new Production(Symbol.FunctionExpr, new ArrayList<Symbol>(Arrays.asList(Symbol.FUNCTION, Symbol.LBRACKET, Symbol.RBRACKET, Symbol.ARROW, Symbol.Expression))));
		productions.add(new Production(Symbol.LetExpr, new ArrayList<Symbol>(Arrays.asList(Symbol.LET, Symbol.ID, Symbol.EQUAL, Symbol.Expression, Symbol.IN, Symbol.Expression))));
		productions.add(new Production(Symbol.IfExpr, new ArrayList<Symbol>(Arrays.asList(Symbol.IF, Symbol.Expression, Symbol.THEN, Symbol.Expression, Symbol.ELSE, Symbol.Expression))));
	
		productions.add(new Production(Symbol.IdentifierList, new ArrayList<Symbol>(Arrays.asList(Symbol.IdentifierList, Symbol.COMMA, Symbol.ID))));
		productions.add(new Production(Symbol.IdentifierList, new ArrayList<Symbol>(Arrays.asList(Symbol.ID))));
	
		productions.add(new Production(Symbol.ExpressionList, new ArrayList<Symbol>(Arrays.asList(Symbol.ExpressionList, Symbol.COMMA, Symbol.Expression))));
		productions.add(new Production(Symbol.ExpressionList, new ArrayList<Symbol>(Arrays.asList(Symbol.Expression))));
	}

	public static void printGrammar() {
		for (int i=0; i<productions.size(); i++) {
			System.out.println(i + ": " + productions.get(i));
		}
	}

	private static Token constructNode(ArrayList<Token> poppedTokens, int rule) {
		Collections.reverse(poppedTokens);
		switch (rule) {
		case 6:
			return new BinaryPrimitiveApplication("|", poppedTokens.get(0), poppedTokens.get(2));
		case 8:
			return new BinaryPrimitiveApplication("&", poppedTokens.get(0), poppedTokens.get(2));
		case 10:
			return new BinaryPrimitiveApplication("==", poppedTokens.get(0), poppedTokens.get(2));
		case 11:
			return new BinaryPrimitiveApplication("=", poppedTokens.get(0), poppedTokens.get(2));
		case 12:
			return new BinaryPrimitiveApplication(">", poppedTokens.get(0), poppedTokens.get(2));
		case 13:
			return new BinaryPrimitiveApplication("<", poppedTokens.get(0), poppedTokens.get(2));
		case 14:
			return new BinaryPrimitiveApplication(">=", poppedTokens.get(0), poppedTokens.get(2));
		case 15:
			return new BinaryPrimitiveApplication("<=", poppedTokens.get(0), poppedTokens.get(2));
		case 17:
			return new BinaryPrimitiveApplication("@", poppedTokens.get(0), poppedTokens.get(2));
		case 19:
			return new BinaryPrimitiveApplication(":", poppedTokens.get(0), poppedTokens.get(2));
		case 21:
			return new BinaryPrimitiveApplication("+", poppedTokens.get(0), poppedTokens.get(2));
		case 22:
			return new BinaryPrimitiveApplication("-", poppedTokens.get(0), poppedTokens.get(2));
		case 24:
			return new BinaryPrimitiveApplication("*", poppedTokens.get(0), poppedTokens.get(2));
		case 25:
			return new BinaryPrimitiveApplication("/", poppedTokens.get(0), poppedTokens.get(2));
		case 27:
			return new UnaryPrimitiveApplication("!", poppedTokens.get(1));
		case 28:
			return new UnaryPrimitiveApplication("-", poppedTokens.get(1));
		case 29:
			return new UnaryPrimitiveApplication("#", poppedTokens.get(1));
		case 30:
			return new UnaryPrimitiveApplication("~", poppedTokens.get(1));
		case 32:
			return poppedTokens.get(1);
		case 39:
			return new PairConstant(poppedTokens.get(1));
		case 40:
			return new Application(poppedTokens.get(0), poppedTokens.get(2));
		case 41:
			return new Application(poppedTokens.get(0), null);
		case 42:
			return new FunctionExpression(poppedTokens.get(2), poppedTokens.get(5));
		case 43:
			return new FunctionExpression(null, poppedTokens.get(4));
		case 44:
			return new LetExpression(poppedTokens.get(1), poppedTokens.get(3), poppedTokens.get(5));
		case 45:
			return new IfExpression(poppedTokens.get(1), poppedTokens.get(3), poppedTokens.get(5));
		case 46:
		case 48:
		{
			Token tokenList = poppedTokens.get(0);
			assert tokenList instanceof TokenList;
			((TokenList) tokenList).tokens.add(poppedTokens.get(2));
			return tokenList;
		}
		case 47:
		case 49:
		{
			TokenList tokenList = new TokenList();
			tokenList.tokens.add(poppedTokens.get(0));
			return tokenList;
		}
		default:
			return poppedTokens.get(0);
		}
	}
	
	public static Token parse(ArrayList<Token> input) {	

		HashMap<Pair<Integer, Symbol>, ParseTableAction> parseTable = generateParseTable(productions, Symbol.Expression);
		// ArrayList<Token> input = new ArrayList<>(Arrays.asList(new Identifier(1), new Operator("+"), new Identifier(7), new Operator("*"), new Identifier(2)));
//		ArrayList<Token> input = new ArrayList<>(Arrays.asList(new Keyword("let"), new Identifier(0), new Operator("="), new Identifier(1), new Operator("+"), new Identifier(7), new Operator("*"), new Identifier(2), new Keyword("in"), new Identifier(3)));
//		ArrayList<Token> input = new ArrayList<>(Arrays.asList(new Keyword("if"), new Identifier(0), new Keyword("then"), new Identifier(1), new Keyword("else"), new Identifier(2)));
//		ArrayList<Token> input = new ArrayList<>(Arrays.asList(new Keyword("fun"), new Keyword("("), new Identifier(0), new Keyword(","), new Identifier(1), new Keyword(","), new Identifier(3), new Keyword(")"), new Keyword("->"), new Identifier(1)));
//		ArrayList<Token> input = new ArrayList<>(Arrays.asList(new Keyword("fun"), new Keyword("("), new Identifier("0"), new Keyword(","), new Identifier("1"), new Keyword(","), new Identifier("3"), new Keyword(")"), new Keyword("->"), new Identifier("1"), new Keyword("("), new Identifier("7"), new Keyword(","), new Identifier("6"), new Keyword(","), new Identifier("5"), new Keyword(")")));
//		ArrayList<Token> input = new ArrayList<>(Arrays.asList(new Identifier(0), new Keyword("("), new Identifier(1), new Operator("+"), new Identifier(2), new Keyword(")")));
//		ArrayList<Token> input = new ArrayList<>(Arrays.asList(new Keyword("("), new Identifier(1), new Operator("+"), new Identifier(2), new Keyword(","), new Identifier(1), new Operator("*"), new Identifier(2), new Keyword(")")));
//		ArrayList<Token> input = new ArrayList<>(Arrays.asList(new Keyword("("), new Identifier(1), new Operator("+"), new Identifier(2), new Keyword(")"), new Operator("*"), new Operator("-"), new Identifier(3)));
//		ArrayList<Token> input = new ArrayList<>(Arrays.asList(new Keyword("("), new Identifier(1), new Operator("&"), new Identifier(2), new Keyword(")"), new Operator("|"), new Operator("-"), new Identifier(3)));
		Token syntaxTree = parse(input, productions, parseTable);
//		System.out.println(syntaxTree);
		return syntaxTree;
//		System.out.println("DONE!!!");

//		System.out.println(first(Symbol.Value, productions));
//		System.out.println(follow(Symbol.Value, productions, Symbol.Expression));
		
//		ArrayList<Production> testproductions = new ArrayList<Production>();
//		testproductions.add(new Production(Symbol.X, new ArrayList<Symbol>(Arrays.asList(Symbol.Y, Symbol.c))));
//		testproductions.add(new Production(Symbol.X, new ArrayList<Symbol>(Arrays.asList(Symbol.a))));
//		testproductions.add(new Production(Symbol.Y, new ArrayList<Symbol>(Arrays.asList(Symbol.b, Symbol.Z))));
//		testproductions.add(new Production(Symbol.Y, new ArrayList<Symbol>(Arrays.asList(Symbol.EPSILON))));
//		testproductions.add(new Production(Symbol.Z, new ArrayList<Symbol>(Arrays.asList(Symbol.EPSILON))));
//
//		beginning = new Item(new Production(Symbol.START, new ArrayList<Symbol>(Arrays.asList(Symbol.X))));
//		
//		System.out.println(first(Symbol.X, testproductions));
//		System.out.println(first(Symbol.Y, testproductions));
//		System.out.println(first(Symbol.Z, testproductions));
//		
//		System.out.println(follow(Symbol.X, testproductions, Symbol.X));
//		System.out.println(follow(Symbol.Y, testproductions, Symbol.X));
//		System.out.println(follow(Symbol.Z, testproductions, Symbol.X));
		
//		ArrayList<Token> arithmeticInput = new ArrayList<>(Arrays.asList(new Identifier(0), new Operator("+"), new Identifier(1), new Operator("*"), new Identifier(2)));
//		Token arithmeticResult = new Sum(new Identifier(0), new Product(new Identifier(1), new Identifier(2)));
//		assertEquals(parse(arithmeticInput, productions, parseTable).toString(), arithmeticResult.toString());

	}
	
	
	private static HashSet<Symbol> first(Symbol X, ArrayList<Production> productions) {
		HashSet<Symbol> result = new HashSet<>();
		
		if (X == Symbol.EPSILON || X.isTerminal()) {
			result.add(X);
		}
		else {
			assert X.isNonterminal();
			for (Production p : productions) {
				if (p.LHS == X) {
					boolean allContainEpsilon = true;
					for (Symbol Y : p.RHS) {
						HashSet<Symbol> yFirst = first(Y, productions);
						result.addAll(yFirst);
						if (!yFirst.contains(Symbol.EPSILON)) {
							allContainEpsilon = false;
							break;
						}
						else {
							result.remove(Symbol.EPSILON);
							// and continue the loop
						}
					}
					
					if (allContainEpsilon) {
						result.add(Symbol.EPSILON);
					}
				}
			}
		}
		
		return result;
	}
	
	private static HashSet<Symbol> follow(Symbol a, ArrayList<Production> productions, Symbol startNonterminal) {
		return follow(a, productions, startNonterminal, new HashSet<Symbol>());
	}

	private static HashSet<Symbol> follow(Symbol a, ArrayList<Production> productions, Symbol startNonterminal, HashSet<Symbol> computed) {
		
		// follow is only defined for nonterminals
		assert a.isNonterminal();
				
		HashSet<Symbol> result = new HashSet<>();

		if (computed.contains(a)) {
			return result;
		}
		computed.add(a);

		if (a == startNonterminal) {
			result.add(Symbol.EOF);
		}
		
		for (Production p : productions) {
			if (p.RHS.contains(a)) {
				int index = p.RHS.indexOf(a);
				boolean aIsLastNonterminal = index == p.RHS.size()-1;
				if (aIsLastNonterminal) {
					result.addAll(follow(p.LHS, productions, startNonterminal, computed));
				}
				else {
					// don't just add the first() of the first occurrence - do it for all occurrences!!
					for (int i=0; i<p.RHS.size()-1; i++) {
						if (p.RHS.get(i) == a) {
							Symbol next = p.RHS.get(i + 1);
							HashSet<Symbol> nextFirst = first(next, productions);
							if (nextFirst.contains(Symbol.EPSILON)) {
								result.addAll(follow(p.LHS, productions, startNonterminal, computed));
								nextFirst.remove(Symbol.EPSILON);
							}
							result.addAll(nextFirst);
						}
					}
				}
			}
		}
		
		// epsilon is never in a follow set
		assert !result.contains(Symbol.EPSILON);
		
		return result;
	}

	private static HashMap<Pair<Integer, Symbol>, ParseTableAction> generateParseTable(ArrayList<Production> productions, Symbol startSymbol) {
		
		// augment the grammar with a new start symbol
		Item augmentedStartRule = new Item(new Production(Symbol.START, new ArrayList<Symbol>(Arrays.asList(startSymbol))));
		
		// closure of initial item set
		ItemSet initialState = new ItemSet();
		initialState.closure(new HashSet<Item>(Arrays.asList(augmentedStartRule)), productions);

		// records transitions between states with triggering symbols
		HashMap<Pair<Integer, Symbol>, Integer> stateTransitionTable = new HashMap<Pair<Integer, Symbol>, Integer>();
	
		// maps state numbers to actual ItemSet instances
		ArrayList<ItemSet> stateRegistry = new ArrayList<>();
		stateRegistry.add(initialState); // state 0
	
		Stack<Pair<ItemSet, Symbol>> pendingTransitions = new Stack<Pair<ItemSet, Symbol>>();
		
		HashSet<Symbol> transitionSymbols = initialState.possibleTransitions();
		for (Symbol s : transitionSymbols) {
			pendingTransitions.push(new Pair<ItemSet, Symbol>(initialState, s));
		}
		
		while (pendingTransitions.size() > 0) {
			Pair<ItemSet, Symbol> currentTransition = pendingTransitions.pop();
	
			int currentIndex = stateRegistry.indexOf(currentTransition.left);
	
			ItemSet nextState = currentTransition.left.nextItemSet(currentTransition.right, productions);
			if (stateRegistry.contains(nextState)) {
				// find it
				Integer newIndex = stateRegistry.indexOf(nextState);
				// register a transition from the current state to that state
				stateTransitionTable.put(new Pair<Integer, Symbol>(currentIndex, currentTransition.right), newIndex);
			}
			else {
				stateRegistry.add(nextState);
				Integer newIndex = stateRegistry.size()-1;
				// register a transition
				stateTransitionTable.put(new Pair<Integer, Symbol>(currentIndex, currentTransition.right), newIndex);
	
				// add transitions from the new state to pending transitions
				HashSet<Symbol> newTransitionSymbols = nextState.possibleTransitions();
				for (Symbol s : newTransitionSymbols) {
					pendingTransitions.push(new Pair<ItemSet, Symbol>(nextState, s));
				}
			}
		}
		
//		System.out.println("State information:");
//		System.out.println(stateRegistry.size());
//		for (int i=0; i<stateRegistry.size(); i++) {
//			System.out.println(i + ": " + stateRegistry.get(i));
//		}
//		System.out.println();

//		System.out.println("Transition information:");
//		for (Pair<Integer, Symbol> key : stateTransitionTable.keySet()) {
//			System.out.println(key.toString() + " | " + stateTransitionTable.get(key));
//		}
		
		// actual SLR parse table (ACTION)
		HashMap<Pair<Integer, Symbol>, ParseTableAction> parseTable = new HashMap<Pair<Integer, Symbol>, ParseTableAction>();
		
		// 1. copy nonterminal columns as transitions
		
		for (Pair<Integer, Symbol> nonterminalKey : stateTransitionTable.keySet()) {
			if (nonterminalKey.right.isNonterminal()) {
				parseTable.put(nonterminalKey, new Transition(stateTransitionTable.get(nonterminalKey)));
			}
		}
	
		// 2. copy terminal columns as shift actions
		
		for (Pair<Integer, Symbol> terminalKey : stateTransitionTable.keySet()) {
			if (terminalKey.right.isTerminal()) {
				parseTable.put(terminalKey, new Shift(stateTransitionTable.get(terminalKey)));
			}
		}
		
		// 3. add $ column
		
		for (int i=0; i<stateRegistry.size(); ++i) {
			if (stateRegistry.get(i).items.contains(augmentedStartRule.shiftRight())) {
				parseTable.put(new Pair<Integer, Symbol>(i, Symbol.EOF), new Accept());
			}
		}
		
		// 4. add reduce rows for fully advanced items, but only if they are in the follow set of the LHS of the production
		
		// leaving out the augmented start rule
		for (int i=0; i<stateRegistry.size(); ++i) {
			Item fullyAdvancedItem = stateRegistry.get(i).getFullyAdvancedItem();
			if (fullyAdvancedItem != null && !fullyAdvancedItem.equals(augmentedStartRule) && !fullyAdvancedItem.equals(augmentedStartRule.shiftRight())) {
				// find matching rule
				int ruleNumber = -1;
				for (int j=0; j<productions.size(); j++) {
					if (fullyAdvancedItem.correspondsToProduction(productions.get(j))) {
						ruleNumber = j;
						break;
					}
				}
				
				assert (ruleNumber != -1);
				for (Symbol terminal : follow(productions.get(ruleNumber).LHS, productions, augmentedStartRule.LHS)) {
					parseTable.put(new Pair<Integer, Symbol>(i, terminal), new Reduce(ruleNumber));
				}
				// leave out nonterminals
				parseTable.put(new Pair<Integer, Symbol>(i, Symbol.EOF), new Reduce(ruleNumber));
			}
		}
		
//		System.out.println("--------------");
		for (int i=0; i<stateRegistry.size(); ++i) {
//			System.out.println(i);
			if (Toyscript.PRINT_STATES) System.out.println(i + ": " + stateRegistry.get(i) + "\n");
//			System.out.println();
		}
//		for (Pair<Integer, Symbol> key : parseTable.keySet()) {
//			System.out.println(key.toString() + " -> " + parseTable.get(key));
//		}
//		System.out.println();
	
		return parseTable;
	}

	private static Token parse(ArrayList<Token> input, ArrayList<Production> productions, HashMap<Pair<Integer, Symbol>, ParseTableAction> parseTable) {
				
		Collections.reverse(input);
		Stack<Token> tokens = new Stack<>();
		tokens.addAll(input);
		
		Stack<Token> parseStack = new Stack<>();

		Stack<ParseState> stateStack = new Stack<>();
		stateStack.push(new ParseState(0, null));
		
		while (true) {
			int currentState = stateStack.peek().state;
			Token lookahead = tokens.size() > 0 ? tokens.peek() : new EOF();
			
			ParseTableAction currentAction = parseTable.get(new Pair<Integer, Symbol>(currentState, lookahead.toSymbol()));
			
			if (currentAction == null) {
				System.err.println("Error: no action for lookahead token " + lookahead + " in parse state " + currentState);
				assert false;
			}
			
			if (currentAction instanceof Shift) {
				Token terminal = tokens.pop();
				parseStack.push(terminal);
				stateStack.push(new ParseState(((Shift) currentAction).state, terminal.toSymbol()));
				if (Toyscript.PRINT_PARSE_OUTPUT) System.out.println("Shifted " + terminal);
			}
			else if (currentAction instanceof Reduce) {
				int rule = ((Reduce) currentAction).rule;
				
				ArrayList<Token> poppedTokens = new ArrayList<>();
				for (int i=0; i<productions.get(rule).RHS.size(); ++i) {
					stateStack.pop();
					poppedTokens.add(parseStack.pop());
				}
				parseStack.push(constructNode(poppedTokens, rule));
				
				if (Toyscript.PRINT_PARSE_OUTPUT) System.out.println("Reduced by rule " + rule + ": " + productions.get(rule));
				int cstate = stateStack.peek().state;
				ParseTableAction transition = parseTable.get(new Pair<Integer, Symbol>(cstate, productions.get(rule).LHS));
				assert transition instanceof Transition;
				
				stateStack.push(new ParseState(((Transition) transition).state, productions.get(rule).LHS));
			}
//			else if (currentAction instanceof Transition) {
//				
//			}
			else {
				assert currentAction instanceof Accept;
				break;
			}
		}
		
		return parseStack.pop();
	}

}
