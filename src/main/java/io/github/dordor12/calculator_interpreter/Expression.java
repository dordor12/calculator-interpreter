package io.github.dordor12.calculator_interpreter;

import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class Expression {
    
    @Getter
    @AllArgsConstructor
    public static class UnaryOperator extends Expression{
        private TokenType tokenType;
        private Expression expression;
    }

    @Getter
    @AllArgsConstructor
    public static class PostfixUnary extends Expression{
        private TokenType tokenType;
        private Expression expression;
    }

    @Getter
    @AllArgsConstructor
    public static class BinaryOperator extends Expression{
        private TokenType tokenType;
        private Expression left;
        private Expression right;
    }

    @Getter
    @AllArgsConstructor
    public static class Literal extends Expression {
        private Value value;
    }

    @Getter
    @AllArgsConstructor
    public static class Variable extends Expression {
        private String name;
    }

}
