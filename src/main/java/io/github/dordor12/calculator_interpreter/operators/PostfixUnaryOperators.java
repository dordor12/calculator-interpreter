package io.github.dordor12.calculator_interpreter.operators;

import io.github.dordor12.calculator_interpreter.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostfixUnaryOperators {
    PLUS_PLUS(TokenType.PLUS_PLUS, 5),
    MINUS_MINUS(TokenType.MINUS_MINUS, 5);
    
    private TokenType tokenType;
    private int priority;
}
