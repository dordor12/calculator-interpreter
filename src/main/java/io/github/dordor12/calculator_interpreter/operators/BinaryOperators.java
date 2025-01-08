package io.github.dordor12.calculator_interpreter.operators;

import io.github.dordor12.calculator_interpreter.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BinaryOperators {
    ADDITION(TokenType.PLUS, 1),
    SUBTRACTION(TokenType.MINUS, 2),
    MULTIPLICATION(TokenType.MULTIPLY, 3),
    DIVISION(TokenType.DIVIDE, 4);

    private TokenType tokenType;
    private int priority;
}