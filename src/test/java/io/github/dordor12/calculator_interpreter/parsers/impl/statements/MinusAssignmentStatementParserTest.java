package io.github.dordor12.calculator_interpreter.parsers.impl.statements;

import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Statement;
import io.github.dordor12.calculator_interpreter.dtos.Token;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;
import io.github.dordor12.calculator_interpreter.parsers.impl.ExpressionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;





public class MinusAssignmentStatementParserTest {

    private MinusAssignmentStatementParser parser;
    private ExpressionParser expressionParser;

    @BeforeEach
    public void setUp() {
        expressionParser = Mockito.mock(ExpressionParser.class);
        parser = new MinusAssignmentStatementParser(expressionParser);
    }

    @Test
    public void testParse_ValidMinusAssignment() {
        List<Token> tokens = List.of(
                new Token(TokenType.IDENTIFIER, "x", null, 1),
                new Token(TokenType.MINUS_EQUALS, "-=", null, 1),
                new Token(TokenType.NUMBER, "5", null, 1)
        );

        Expression expression = Mockito.mock(Expression.class);
        when(expressionParser.parse(anyList())).thenReturn(expression);

        Statement.Assignment result = parser.parse(tokens);

        assertNotNull(result);
        assertTrue(result instanceof Statement.Assignment.Minus);
        assertEquals(tokens.get(0), ((Statement.Assignment.Minus) result).getVariable());
        assertEquals(expression, ((Statement.Assignment.Minus) result).getExpression());
    }

    @Test
    public void testParse_InvalidMinusAssignment() {
        List<Token> tokens = List.of(
                new Token(TokenType.IDENTIFIER, "x", null, 1),
                new Token(TokenType.PLUS_EQUALS, "+=", null, 1),
                new Token(TokenType.NUMBER, "5", null, 1)
        );

        Statement.Assignment result = parser.parse(tokens);

        assertNull(result);
    }

    @Test
    public void testParse_EmptyTokens() {
        List<Token> tokens = List.of();

        Statement.Assignment result = parser.parse(tokens);

        assertNull(result);
    }

    @Test
    public void testParse_NotEnoughTokens() {
        List<Token> tokens = List.of(
                new Token(TokenType.IDENTIFIER, "x", null, 1),
                new Token(TokenType.MINUS_EQUALS, "-=", null, 1)
        );

        Statement.Assignment result = parser.parse(tokens);

        assertNull(result);
    }
}