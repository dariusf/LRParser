
# Implementation of an SLR(1) Parser

## Testing the Parser

An executable jar, `toyscript.jar`, is provided. It is a command-line interpreter integrating JFlex and this project as the frontend. To run it:

```sh
java -jar toyscript.jar <filename> [flags]
```

The output will be the result of evaluating the Toyscript code in the file. Flags are optional and will modify and/or augment the behaviour of the interpreter.

Sample Toyscript programs can be found in `test`. Any of them may be evaluated with the interpreter; the results should match the corresponding `.out` files in `test`.

## Flags

```sh
java -jar toyscript.jar --help
```

By default, the parser outputs the result of evaluating the input.

- `-c` `--code`
	- Causes input to the parser to be read from stdin instead. If a filename argument is provided, it will be ignored. Works with all other flags.

- `-v` `--visualise`
	- Launches a visualisation of the syntax tree constructed by the parser when evaluation is complete. The folder `visualisation` has to be present in the same directory as the jar archive for this to work. Works with all other flags.

- `-j` `--json`
	- Outputs a JSON representation of syntax tree that the parser constructs instead of the result of evaluation.

- `-o` `--parser-output`
	- Prints the actions taken by the parser (shifts and reductions) to produce the AST as it parses the input. Prepended to the normal output of the parser.

- `-g` `--grammar`
	- Prints Toyscript's grammar. Does nothing to the input.

- `-h` `--help`
	- Shows this help message. Does nothing to the input.

- `-s` `--parse-states`
	- Prints the parse states derived from the grammar. Does nothing to the input.

## Toyscript

Toyscript is an ancillary language created to demonstate the parser frontend developed in this project. It is minimal, with syntax resembling that of OCaml. The grammar may be expressed in EBNF as follows:

	<expression> ::= <number> | <boolean> | <pair>
	               | <id>
	               | (<expression>)
	               | <unary-op> <expression>
	               | <expression> <binary-op> <expression>
	               | if <expression> then <expression> else <expression>
	               | let <id> = <expression> in <expression>
	               | fun ({<id>}) -> <expression>
	               | <id>({<expression>})

A more detailed description of Toyscript may be found in the accompanying project report.