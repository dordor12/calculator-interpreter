package io.github.dordor12.calculator_interpreter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenType {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    OPEN_PARENTHESES("("),
    CLOSE_PARENTHESES(")"),
    NUMBER(null),
    EQUALS("="),
    PLUS_EQUALS("+="),
    MINUS_EQUALS("-="),
    PLUS_PLUS("++"),
    MINUS_MINUS("--"),
    NEW_LINE("\n"),
    IDENTIFIER(null),
    EOF("EOF");

    public final String value;
}
