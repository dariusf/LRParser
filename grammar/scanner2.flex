/* JFlex example: part of Java 1.0/1.1 language lexer specification */

package syntax;

/*
import java_cup.runtime.*;
*/
import token.*;
import syntax.*;

@SuppressWarnings("unused")

%%

%public
%class scanner2
%unicode
%type Token
%function nextToken
/*%cup*/
%line
%column

%{
/*
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
*/
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
"#"              { return new Operator(yytext()); }
"~"              { return new Operator(yytext()); }
"@"              { return new Operator(yytext()); }
":"              { return new Operator(yytext()); }
"->"              { return new Keyword(yytext()); }
"let"              { return new Keyword(yytext()); }
"in"              { return new Keyword(yytext()); }
"="                { return new Operator(yytext()); }
"if"               { return new Keyword(yytext()); }
"then"               { return new Keyword(yytext()); }
"else"             { return new Keyword(yytext()); }
"fun"         { return new Keyword(yytext()); }
","                { return new Keyword(yytext()); }
"[]"               { return new EmptyList(); }
"("                { return new Keyword(yytext()); }
")"                { return new Keyword(yytext()); }
"+"                { return new Operator(yytext()); }
"-"                { return new Operator(yytext()); }
"*"                { return new Operator(yytext()); }
"/"                { return new Operator(yytext()); }
"&"               { return new Operator(yytext()); }
"|"               { return new Operator(yytext()); }
"!"                { return new Operator(yytext()); }
"=="              { return new Operator(yytext()); }
"<"                { return new Operator(yytext()); }
">"                { return new Operator(yytext()); }
"<="               { return new Operator(yytext()); }
">="               { return new Operator(yytext()); }
"true"             { return new BooleanConstant(true); }
"false"            { return new BooleanConstant(false); }
"["                { return new Keyword(yytext()); }
"]"                { return new Keyword(yytext()); }
// "%"                 { return symbol(sym.MOD); }
// "!=="              { return symbol(sym.NOTEQUALEQUAL); }


  /* identifiers */ 
  {Identifier}                   { return new Identifier(yytext()); }

/* number */ 
{DecNumberLiteral} { 
		     double x = 0;
		     String yy = yytext();
                     try { x = Double.parseDouble(yy); } 
                     catch(NumberFormatException nfe) { 
                        System.out.println("wrong int format: "+yy+
                                           "\ninternal error in scanner"); } 
                     return new NumberConstant(new Double(x));
                   }
 
/* String */ 
{SingleQuoteString} { 
		     String yy = yytext();
		     yy = yy.substring(1,yy.length()-1);
		     yy = yy.replace("\"", "\\\"");
		     yy = "\"" + yy + "\"";
                     return new StringConstant(yy);
                   }
 
{DoubleQuoteString} { 
		     String yy = yytext();
                     return new StringConstant(yy);
                   }
 
  /* comments */
  {Comment}                      { /* ignore */ }
 
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
 

/* error fallback */
.|\n                             { throw new Error("Illegal character <"+

                                                    yytext()+">"); }
