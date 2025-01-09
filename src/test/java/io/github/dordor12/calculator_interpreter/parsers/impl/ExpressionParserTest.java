package io.github.dordor12.calculator_interpreter.parsers.impl;

import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;import io.github.dordor12.calculator_interpreter.TokenMappingFactory;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;
import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Token;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;



public class ExpressionParserTest {
    private static TokenMappingFactory tokenMappingFactory = new TokenMappingFactory();
    private static ExpressionParser parser;

    @BeforeAll
    public static void setUp() {
        parser = new ExpressionParser(tokenMappingFactory.getUnaryOperatorsMapping(), tokenMappingFactory.getBinaryOperatorsMapping(), tokenMappingFactory.getPostfixUnaryOperatorsMapping());
    }

    @Test
    public void testParseLiteral() {
        List<Token> tokens = List.of(new Token(TokenType.NUMBER, "5", 5.0, 1));
        Expression result = parser.parse(tokens);
        assertTrue(result instanceof Expression.Literal);
        assertEquals(CalcTypes.NUMBER, ((Expression.Literal) result).getValue().getType());
        assertEquals(5.0, ((Expression.Literal) result).getValue().getValue());
    }

    @Test
    public void testParseVariable() {
        List<Token> tokens = List.of(new Token(TokenType.IDENTIFIER, "x", null, 1));
        Expression result = parser.parse(tokens);
        assertTrue(result instanceof Expression.Variable);
        assertEquals("x", ((Expression.Variable) result).getName());
    }

    @Test
    public void testParseUnaryOperator() {
        List<Token> tokens = List.of(new Token(TokenType.PLUS_PLUS, "++", null, 1), new Token(TokenType.NUMBER, "5", 5.0, 1));
        Expression result = parser.parse(tokens);
        assertTrue(result instanceof Expression.UnaryOperator);
        assertEquals(TokenType.PLUS_PLUS, ((Expression.UnaryOperator) result).getTokenType());
        assertTrue(((Expression.UnaryOperator) result).getExpression() instanceof Expression.Literal);
    }

    @Test
    public void testParseBinaryOperator() {
        List<Token> tokens = List.of(new Token(TokenType.NUMBER, "5", 5.0, 1), new Token(TokenType.PLUS, "+", null, 1), new Token(TokenType.NUMBER, "3", 3.0, 1));
        Expression result = parser.parse(tokens);
        assertTrue(result instanceof Expression.BinaryOperator);
        assertEquals(TokenType.PLUS, ((Expression.BinaryOperator) result).getTokenType());
        assertTrue(((Expression.BinaryOperator) result).getLeft() instanceof Expression.Literal);
        assertTrue(((Expression.BinaryOperator) result).getRight() instanceof Expression.Literal);
    }

    @Test
    public void testParseParentheses() {
        List<Token> tokens = List.of(new Token(TokenType.OPEN_PARENTHESES, "(", null, 1), new Token(TokenType.NUMBER, "5", 5.0, 1), new Token(TokenType.CLOSE_PARENTHESES, ")", null, 1));
        Expression result = parser.parse(tokens);
        assertTrue(result instanceof Expression.Literal);
        assertEquals(CalcTypes.NUMBER, ((Expression.Literal) result).getValue().getType());
        assertEquals(5.0, ((Expression.Literal) result).getValue().getValue());
    }

    @Test
    public void testParseEmptyTokens() {
        List<Token> tokens = List.of();
        assertThrows(IllegalArgumentException.class, () -> parser.parse(tokens));
    }

    @Test
    public void testParseMismatchedParentheses() {
        List<Token> tokens = List.of(new Token(TokenType.OPEN_PARENTHESES, "(", null, 1), new Token(TokenType.NUMBER, "5", 5.0, 1));
        assertThrows(IllegalArgumentException.class, () -> parser.parse(tokens));
    }

    @Test
    public void testParseVariableWithUnaryOperator() {
        List<Token> tokens = List.of(new Token(TokenType.PLUS_PLUS, "++", null, 1), new Token(TokenType.IDENTIFIER, "x", null, 1));
        Expression result = parser.parse(tokens);
        assertTrue(result instanceof Expression.UnaryOperator);
        assertEquals(TokenType.PLUS_PLUS, ((Expression.UnaryOperator) result).getTokenType());
        assertTrue(((Expression.UnaryOperator) result).getExpression() instanceof Expression.Variable);
        assertEquals("x", ((Expression.Variable) ((Expression.UnaryOperator) result).getExpression()).getName());
    }

    @Test
    public void testParseLiteralBinaryOperatorAndVariableWithUnaryOperator() {
        List<Token> tokens = List.of(
            new Token(TokenType.NUMBER, "5", 5.0, 1),
            new Token(TokenType.PLUS, "+", null, 1),
            new Token(TokenType.PLUS_PLUS, "++", null, 1),
            new Token(TokenType.IDENTIFIER, "x", null, 1)
        );
        Expression result = parser.parse(tokens);
        assertTrue(result instanceof Expression.BinaryOperator);
        assertEquals(TokenType.PLUS, ((Expression.BinaryOperator) result).getTokenType());
        assertTrue(((Expression.BinaryOperator) result).getLeft() instanceof Expression.Literal);
        assertTrue(((Expression.BinaryOperator) result).getRight() instanceof Expression.UnaryOperator);
        assertEquals("x", ((Expression.Variable) ((Expression.UnaryOperator) ((Expression.BinaryOperator) result).getRight()).getExpression()).getName());
    }

    @Test
    public void testParseVariableWithUnaryOperatorAndLiteralBinaryOperator() {
        List<Token> tokens = List.of(
            new Token(TokenType.IDENTIFIER, "x", null, 1),
            new Token(TokenType.PLUS_PLUS, "++", null, 1),
            new Token(TokenType.PLUS, "+", null, 1),
            new Token(TokenType.NUMBER, "5", 5.0, 1)
        );
        Expression result = parser.parse(tokens);
        assertTrue(result instanceof Expression.BinaryOperator);
        assertEquals(TokenType.PLUS, ((Expression.BinaryOperator) result).getTokenType());
        assertTrue(((Expression.BinaryOperator) result).getLeft() instanceof Expression.PostfixUnary);
        assertTrue(((Expression.BinaryOperator) result).getRight() instanceof Expression.Literal);
        assertEquals(5.0, ((Expression.Literal) ((Expression.BinaryOperator) result).getRight()).getValue().getValue());
    }

    @Test
    public void testExpressionStartWithNumber() {
        List<Token> tokens = List.of(
            new Token(TokenType.NUMBER, "3", 3.0, 1),
            new Token(TokenType.PLUS, "+", null, 1),
            new Token(TokenType.NUMBER, "5", 5.0, 1)
        );
        Expression result = parser.parse(tokens);
        assertTrue(result instanceof Expression.BinaryOperator);
        var a = (Expression.BinaryOperator) result;
        assertEquals(TokenType.PLUS, ((Expression.BinaryOperator) result).getTokenType());
        assertTrue(((Expression.BinaryOperator) result).getLeft() instanceof Expression.Literal);
        assertTrue(((Expression.BinaryOperator) result).getRight() instanceof Expression.Literal);
        assertEquals(3.0, ((Expression.Literal) a.getLeft()).getValue().getValue());
        assertEquals(5.0, ((Expression.Literal) a.getRight()).getValue().getValue());
    }

    @Test // -3 + (4 * -(5 + 2) + x)
    public void testComplexExpression() {
        List<Token> tokens = List.of(
            new Token(TokenType.MINUS, "-", null, 1),
            new Token(TokenType.NUMBER, "3", 3.0, 1),
            new Token(TokenType.PLUS, "+", null, 1),
            new Token(TokenType.OPEN_PARENTHESES, "(", null, 1),
            new Token(TokenType.NUMBER, "4", 4.0, 1),
            new Token(TokenType.MULTIPLY, "*", null, 1),
            new Token(TokenType.MINUS, "-", null, 1),
            new Token(TokenType.OPEN_PARENTHESES, "(", null, 1),
            new Token(TokenType.NUMBER, "5", 5.0, 1),
            new Token(TokenType.PLUS, "+", null, 1),
            new Token(TokenType.NUMBER, "2", 2.0, 1),
            new Token(TokenType.CLOSE_PARENTHESES, ")", null, 1),
            new Token(TokenType.PLUS, "+", null, 1),
            new Token(TokenType.IDENTIFIER, "x", null, 1),
            new Token(TokenType.CLOSE_PARENTHESES, ")", null, 1)
        );
        Expression result = parser.parse(tokens);
        assertTrue(result instanceof Expression.BinaryOperator);
        assertEquals(TokenType.PLUS, ((Expression.BinaryOperator) result).getTokenType());

        Expression left = ((Expression.BinaryOperator) result).getLeft();
        assertTrue(left instanceof Expression.UnaryOperator);
        assertEquals(TokenType.MINUS, ((Expression.UnaryOperator) left).getTokenType());
        assertTrue(((Expression.UnaryOperator) left).getExpression() instanceof Expression.Literal);
        assertEquals(3.0, ((Expression.Literal) ((Expression.UnaryOperator) left).getExpression()).getValue().getValue());

        Expression right = ((Expression.BinaryOperator) result).getRight();
        assertTrue(right instanceof Expression.BinaryOperator);
        assertEquals(TokenType.PLUS, ((Expression.BinaryOperator) right).getTokenType());

        Expression rightLeft = ((Expression.BinaryOperator) right).getLeft();
        assertTrue(rightLeft instanceof Expression.BinaryOperator);
        assertEquals(TokenType.MULTIPLY, ((Expression.BinaryOperator) rightLeft).getTokenType());

        Expression rightLeftLeft = ((Expression.BinaryOperator) rightLeft).getLeft();
        assertTrue(rightLeftLeft instanceof Expression.Literal);
        assertEquals(4.0, ((Expression.Literal) rightLeftLeft).getValue().getValue());

        Expression rightLeftRight = ((Expression.BinaryOperator) rightLeft).getRight();
        assertTrue(rightLeftRight instanceof Expression.UnaryOperator);
        assertEquals(TokenType.MINUS, ((Expression.UnaryOperator) rightLeftRight).getTokenType());

        Expression rightLeftRightExpr = ((Expression.UnaryOperator) rightLeftRight).getExpression();
        assertTrue(rightLeftRightExpr instanceof Expression.BinaryOperator);
        assertEquals(TokenType.PLUS, ((Expression.BinaryOperator) rightLeftRightExpr).getTokenType());

        Expression rightLeftRightExprLeft = ((Expression.BinaryOperator) rightLeftRightExpr).getLeft();
        assertTrue(rightLeftRightExprLeft instanceof Expression.Literal);
        assertEquals(5.0, ((Expression.Literal) rightLeftRightExprLeft).getValue().getValue());

        Expression rightLeftRightExprRight = ((Expression.BinaryOperator) rightLeftRightExpr).getRight();
        assertTrue(rightLeftRightExprRight instanceof Expression.Literal);
        assertEquals(2.0, ((Expression.Literal) rightLeftRightExprRight).getValue().getValue());

        Expression rightRight = ((Expression.BinaryOperator) right).getRight();
        assertTrue(rightRight instanceof Expression.Variable);
        assertEquals("x", ((Expression.Variable) rightRight).getName());
    }
}