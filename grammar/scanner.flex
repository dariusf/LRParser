/* JFlex example: part of Java 1.0/1.1 language lexer specification */

package syntax;

import java_cup.runtime.*;

@SuppressWarnings("unused")

%%

%public
%class scanner
%unicode
%cup
%line
%column

%{
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }

%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/*" [^*] {CommentContent} "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

DoubleQuoteStringCharacter = [^\\\"] | "\\\"" | \\n
SingleQuoteStringCharacter = [^\\\'] | "\\\'" | \\n
DoubleQuoteString = "\"" {DoubleQuoteStringCharacter}* "\"" 
SingleQuoteString = "\'" {SingleQuoteStringCharacter}* "\'"

Letter = [a-zA-Z]
LetterDigit = [a-zA-Z0-9]
Identifier = {Letter} {LetterDigit}*
/*
[:jletter:] [:jletterdigit:]*
*/

DecNumberLiteral = (0 | [1-9][0-9]*) (\.[0-9]+)?

%state STRING

%%

/* keywords */
"#"              { return symbol(sym.HEAD); }
"~"              { return symbol(sym.TAIL); }
"@"              { return symbol(sym.APPEND); }
":"              { return symbol(sym.CONS); }
"->"              { return symbol(sym.ARROW); }
"let"              { return symbol(sym.LET); }
"in"              { return symbol(sym.IN); }
"="                { return symbol(sym.EQUAL); }
"if"               { return symbol(sym.IF); }
"then"               { return symbol(sym.THEN); }
"else"             { return symbol(sym.ELSE); }
"fun"         { return symbol(sym.FUNCTION); }
","                { return symbol(sym.COMMA); }
"[]"               { return symbol(sym.EMPTYLIST); }
"("                { return symbol(sym.LPAREN); }
")"                { return symbol(sym.RPAREN); }
"+"                { return symbol(sym.PLUS); }
"-"                { return symbol(sym.MINUS); }
// "~"                { return symbol(sym.NEGATE); }
"*"                { return symbol(sym.TIMES); }
"/"                { return symbol(sym.DIV); }
"&"               { return symbol(sym.AND); }
"|"               { return symbol(sym.OR); }
"!"                { return symbol(sym.NOT); }
"=="              { return symbol(sym.EQUALEQUAL); }
"<"                { return symbol(sym.LESS); }
">"                { return symbol(sym.GREATER); }
"<="               { return symbol(sym.LEQ); }
">="               { return symbol(sym.GEQ); }
"true"             { return symbol(sym.TRUE); }
"false"            { return symbol(sym.FALSE); }
// ";"                { return symbol(sym.SEMICOLON); }
// "switch"           { return symbol(sym.SWITCH); }
// "default"          { return symbol(sym.DEFAULT); }
// "case"             { return symbol(sym.CASE); }
// "break"            { return symbol(sym.BREAK); }
// "return"           { return symbol(sym.RETURN); }
// "?"                 { return symbol(sym.QUESTIONMARK); }
// ":"                { return symbol(sym.COLON); }
// "undefined"        { return symbol(sym.UNDEFINED); }
"["                { return symbol(sym.LSQBR); }
"]"                { return symbol(sym.RSQBR); }
// "{"                { return symbol(sym.LCURLY); }
// "}"                { return symbol(sym.RCURLY); }
// "."                { return symbol(sym.DOT); }
// "++"               { return symbol(sym.PLUSPLUS); }
// "--"               { return symbol(sym.MINUSMINUS); }
// "%"                 { return symbol(sym.MOD); }
// "!=="              { return symbol(sym.NOTEQUALEQUAL); }
// "while"            { return symbol(sym.WHILE); }
// "new"            { return symbol(sym.NEW); }
// "Array"            { return symbol(sym.ARRAY); }


  /* identifiers */ 
  {Identifier}                   { return symbol(sym.IDENTIFIER,yytext()); }

/* number */ 
{DecNumberLiteral} { 
		     double x = 0;
		     String yy = yytext();
                     try { x = Double.parseDouble(yy); } 
                     catch(NumberFormatException nfe) { 
                        System.out.println("wrong int format: "+yy+
                                           "\ninternal error in scanner"); } 
                     return symbol(sym.NUMBER,new Double(x)); 
                   }
 
/* String */ 
{SingleQuoteString} { 
		     String yy = yytext();
		     yy = yy.substring(1,yy.length()-1);
		     yy = yy.replace("\"", "\\\"");
		     yy = "\"" + yy + "\"";
                     return symbol(sym.STRING,yy);
                   }
 
{DoubleQuoteString} { 
		     String yy = yytext();
                     return symbol(sym.STRING,yy);
                   }
 
  /* comments */
  {Comment}                      { /* ignore */ }
 
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
 

/* error fallback */
.|\n                             { throw new Error("Illegal character <"+

                                                    yytext()+">"); }
