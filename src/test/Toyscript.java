package test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.UnflaggedOption;

import parser.Parser;

import syntax.Environment;
import syntax.Expression;
import syntax.scanner2;
import token.Token;

public class Toyscript {
	
	private static Expression parse(String code) {
		try {
			scanner2 s = new scanner2(new ByteArrayInputStream(code.getBytes("UTF-8")));
			
			ArrayList<Token> tokens = new ArrayList<>();
			Token next;
			while ((next = s.nextToken()) != null) {
				tokens.add(next);
			}
			
			return (Expression) Parser.parse(tokens);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	public static Expression eval(String code) {
		return parse(code).eval(new Environment());
	}

	public static String toAST(String code) {
		return parse(code).toJSON();
	}
	
	public static void visualise(String code) {
		Utility.writeFile("visualisation/data.js", "var json = " + toAST(code)+ ";");
		Utility.launchFile("visualisation/tree.html");
	}
	
	public static String fileName = null;
	public static boolean READ_CODE_FROM_STDIN = false;
	public static boolean PRINT_JSON = false;
	public static boolean VISUALISE = false;
	public static boolean PRINT_PARSE_OUTPUT = false;
	public static boolean PRINT_GRAMMAR = false;
	public static boolean PRINT_HELP = false;
	public static boolean PRINT_STATES = false;

	private static JSAP parseArguments(String[] args) throws JSAPException {
		JSAP jsap = new JSAP();
		
		UnflaggedOption filename = new UnflaggedOption("filename")
			.setStringParser(JSAP.STRING_PARSER)
			.setRequired(false)
			.setGreedy(false);
		filename.setHelp("the name of the input file");
		
		jsap.registerParameter(filename);

        Switch code = new Switch("code")
            .setShortFlag('c')
            .setLongFlag("code");
        code.setHelp("read code from stdin instead of a file");

        Switch json = new Switch("json")
	        .setShortFlag('j')
	        .setLongFlag("json");
        json.setHelp("output json representation of syntax tree");

        Switch visualise = new Switch("visualise")
            .setShortFlag('v') 
            .setLongFlag("visualise");
        visualise.setHelp("launch a visualisation of the syntax tree");
        
        Switch parserOutput = new Switch("parser-output")
            .setShortFlag('o') 
            .setLongFlag("parser-output");
        parserOutput.setHelp("print the actions taken by the parser to produce the AST");

        Switch states = new Switch("parse-states")
        .setShortFlag('s') 
        .setLongFlag("parse-states");
        states.setHelp("prints the parse states derived from the grammar");

    	Switch grammar = new Switch("grammar")
            .setShortFlag('g')
            .setLongFlag("grammar");
        grammar.setHelp("prints Toyscript's grammar");

        Switch help = new Switch("help")
	        .setShortFlag('h')
	        .setLongFlag("help");
        help.setHelp("shows this help message");

        jsap.registerParameter(code);
        jsap.registerParameter(json);
        jsap.registerParameter(visualise);
        jsap.registerParameter(parserOutput);
        jsap.registerParameter(grammar);
        jsap.registerParameter(help);
        jsap.registerParameter(states);

        JSAPResult config = jsap.parse(args);
        
        if (config.success()) {
        	fileName = config.getString("filename");
        	
        	PRINT_GRAMMAR = config.getBoolean("grammar");
        	PRINT_HELP = config.getBoolean("help");
        	
    		READ_CODE_FROM_STDIN = config.getBoolean("code");

    		PRINT_JSON = config.getBoolean("json");
			VISUALISE = config.getBoolean("visualise");
			PRINT_PARSE_OUTPUT = config.getBoolean("parser-output");
    		PRINT_STATES = config.getBoolean("parse-states");
        }
        else {
            System.err.println("\nUsage: java -jar " + Toyscript.class.getSimpleName() + " " + jsap.getUsage() + "\n\n" + jsap.getHelp());
            System.exit(1);
        }
        
        return jsap;
	}

	public static void main(String[] args) throws Exception {
		
		JSAP jsap = parseArguments(args);
		
		String example = "let fact = fun (n) -> if n = 0 then 1 else n * (fact(n-1)) in fact(4)";
		example = "let map = fun (f, xs) -> if xs == [] then [] else (f(#xs)) : (map(f, ~xs)) in map(fun (x) -> x+1, [1,2,3])";
//		example = "let accumulate = fun (func, initial, xs) -> if xs == [] then initial else func(#xs, accumulate(func, initial, ~xs)) in accumulate(fun (x, y) -> x + y, 0, [1,2,3,4])";
//		example = "let filter = fun (f, xs) -> if xs == [] then [] else if f(#xs) then #xs:(filter(f, ~xs)) else filter(f, ~xs) in filter(fun (x) -> x / 2 < 3, [4,5,6,7,8])";
		
		Parser.init();

		if (PRINT_GRAMMAR) {
			Parser.printGrammar();
		}
		else if (PRINT_HELP) {
            System.err.println("\nUsage: java -jar " + Toyscript.class.getSimpleName() + " " + jsap.getUsage() + "\n\n" + jsap.getHelp());
		}
		else {

			String input = "";
			if (READ_CODE_FROM_STDIN) {
				input = Utility.readFromStdin();
			}
			else {
				if (fileName == null) {
					System.err.println("Error: filename or --code switch required. Use --help for more information.");
					System.exit(1);
				}
				input = Utility.readFile(fileName);
			}
			if (PRINT_JSON) {
				System.out.println(toAST(input));
			}
			else {
				System.out.println(eval(input));
			}
			
			if (VISUALISE) {
				visualise(input);
			}
		}
	}
}
