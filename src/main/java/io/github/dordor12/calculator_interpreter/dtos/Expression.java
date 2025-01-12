package io.github.dordor12.calculator_interpreter.dtos;


import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;


public abstract class Expression {  

    @Getter
    @AllArgsConstructor
    public static abstract class ExprOperator extends Expression{
        private TokenType tokenType;
        private Operator operator;       
    }

    @Getter
    public static class UnaryOperator extends ExprOperator {
        private Expression expression;

        public UnaryOperator(TokenType tokenType, Operator operator, Expression expression) {
            super(tokenType, operator);
            this.expression = expression;
        }
    }

    @Getter
    public static class PostfixUnary extends ExprOperator{
        private Expression expression;

        public PostfixUnary(TokenType tokenType, Operator operator, Expression expression) {
            super(tokenType, operator);
            this.expression = expression;
        }
    }

    @Getter
    public static class BinaryOperator extends ExprOperator{
        private Expression left;
        private Expression right;

        public BinaryOperator(TokenType tokenType, Operator operator, Expression left, Expression right) {
            super(tokenType, operator);
            this.left = left;
            this.right = right;
        }
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
