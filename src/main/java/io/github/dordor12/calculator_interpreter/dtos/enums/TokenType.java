package io.github.dordor12.calculator_interpreter.dtos.enums;

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
    EOF(null);

    public final String value;
}
