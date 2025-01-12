package io.github.dordor12.calculator_interpreter.dtos.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Operator {
    BINARY_ADDITION(OperatorType.BinaryOperator, TokenType.PLUS, 1),
    BINARY_SUBTRACTION(OperatorType.BinaryOperator, TokenType.MINUS, 2),
    BINARY_MULTIPLICATION(OperatorType.BinaryOperator, TokenType.MULTIPLY, 3),
    BINARY_DIVISION(OperatorType.BinaryOperator,TokenType.DIVIDE, 4),
    UNARY_MINUS(OperatorType.UnaryOperator, TokenType.MINUS, 5),
    UNARY_PLUS(OperatorType.UnaryOperator, TokenType.PLUS, 5),
    UNARY_PLUS_PLUS(OperatorType.UnaryOperator,TokenType.PLUS_PLUS, 5),
    UNARY_MINUS_MINUS(OperatorType.UnaryOperator,TokenType.MINUS_MINUS, 5),
    POSTFIX_PLUS_PLUS(OperatorType.PostfixUnaryOperator,TokenType.PLUS_PLUS, 5),
    POSTFIX_MINUS_MINUS(OperatorType.PostfixUnaryOperator,TokenType.MINUS_MINUS, 5);
    
    private OperatorType operatorType;
    private TokenType tokenType;
    private int priority;
}
