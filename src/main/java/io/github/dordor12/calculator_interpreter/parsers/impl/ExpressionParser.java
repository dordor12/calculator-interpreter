package io.github.dordor12.calculator_interpreter.parsers.impl;

import io.github.dordor12.calculator_interpreter.CalcTypes;
import io.github.dordor12.calculator_interpreter.Expression;
import io.github.dordor12.calculator_interpreter.Token;
import io.github.dordor12.calculator_interpreter.TokenType;
import io.github.dordor12.calculator_interpreter.Value;
import io.github.dordor12.calculator_interpreter.operators.BinaryOperators;
import io.github.dordor12.calculator_interpreter.operators.PostfixUnaryOperators;
import io.github.dordor12.calculator_interpreter.operators.UnaryOperators;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExpressionParser {

    private final Map<TokenType, UnaryOperators> unaryOperatorsMapping;
    private final Map<TokenType, BinaryOperators> binaryOperatorsMapping;
    private final Map<TokenType, PostfixUnaryOperators> postfixUnaryOperatorsMapping;

    public Expression parse(List<Token> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Token list is empty");
        }

        return new Parser(tokens).parseExpression(1);
    }

    private class Parser {
        private final List<Token> tokens;
        private int current = 0;

        public Parser(List<Token> tokens) {
            this.tokens = tokens;
        }

        // Parse an expression with a given precedence
        private Expression parseExpression(int precedence) {
            Expression left = parseUnary(); // Parse a unary operator or primary expression

            while (!isAtEnd()) {
                Token operator = peek();
                BinaryOperators info = binaryOperatorsMapping.get(operator.getType());

                // If the operator's precedence is less than the current precedence, stop
                // parsing
                if (info == null || info.getPriority() < precedence) {
                    break;
                }

                // Advance and parse the right-hand side of the binary operation
                advance();
                int nextPrecedence = info.getPriority() + 1;
                Expression right = parseExpression(nextPrecedence);

                // Create a binary operation node
                left = new Expression.BinaryOperator(operator.getType(), left, right);
            }

            return left;
        }

        // Parse unary operators
        private Expression parseUnary() {
            Token operator = peek();
            if (unaryOperatorsMapping.containsKey(operator.getType())) {
                advance();
                Expression operand = parseUnary(); // Recursively parse the operand
                return new Expression.UnaryOperator(operator.getType(), operand);
            }
            return parsePostfix();
        }

        private Expression parsePostfix() {
            Expression expr = parsePrimary();
    
            while (!isAtEnd()) {
                Token operator = peek();
                if (postfixUnaryOperatorsMapping.containsKey(operator.getType())) {
                    advance();
                    expr = new Expression.PostfixUnary(operator.getType(), expr);
                } else {
                    break;
                }
            }
    
            return expr;
        }

        // Parse primary expressions (literals, variables, or parenthesized expressions)
        private Expression parsePrimary() {
            if (match(TokenType.NUMBER)) {
                var value = new Value(CalcTypes.NUMBER, previous().getValue());
                return new Expression.Literal(value);
            }

            if (match(TokenType.IDENTIFIER)) {
                return new Expression.Variable(previous().getStringValue());
            }

            if (match(TokenType.OPEN_PARENTHESES)) {
                Expression expr = parseExpression(1); // Start parsing the inner expression
                consume(TokenType.CLOSE_PARENTHESES, "Expected ')'");
                return expr;
            }

            throw new RuntimeException("Expected expression.");
        }

        // Utility methods for parsing

        private boolean match(TokenType type) {
            if (isAtEnd())
                return false;
            if (peek().getType() != type)
                return false;
            advance();
            return true;
        }

        private Token consume(TokenType type, String message) {
            if (peek().getType() == type) {
                return advance();
            }
            throw new RuntimeException(message);
        }

        private boolean isAtEnd() {
            return current >= tokens.size();
        }

        private Token advance() {
            if (!isAtEnd())
                current++;
            return previous();
        }

        private Token peek() {
            return tokens.get(current);
        }

        private Token previous() {
            return tokens.get(current - 1);
        }

    }

}