package io.github.dordor12.calculator_interpreter.parsers.impl.statements;

import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Statement;
import io.github.dordor12.calculator_interpreter.dtos.Token;
import io.github.dordor12.calculator_interpreter.dtos.Value;
import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;
import io.github.dordor12.calculator_interpreter.parsers.impl.ExpressionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;



public class AssignmentStatementParserTest {

    private AssignmentStatementParser assignmentStatementParser;
    private ExpressionParser expressionParser;

    @BeforeEach
    public void setUp() {
        expressionParser = Mockito.mock(ExpressionParser.class);
        assignmentStatementParser = new AssignmentStatementParser(expressionParser);
    }

    @Test
    public void testParseValidAssignment() {
        List<Token> tokens = List.of(
                new Token(TokenType.IDENTIFIER, "x", null, 1),
                new Token(TokenType.EQUALS, "=", null, 1),
                new Token(TokenType.NUMBER, "5", null, 1)
        );

        Expression.Literal expression = new Expression.Literal(new Value(CalcTypes.NUMBER,5.0));
        when(expressionParser.parse(anyList())).thenReturn(expression);

        Statement.Assignment assignment = assignmentStatementParser.parse(tokens);

        assertNotNull(assignment);
        assertEquals(tokens.get(0), assignment.getVariable());
        assertEquals(expression, assignment.getExpression());
    }

    @Test
    public void testParseInvalidAssignment() {
        List<Token> tokens = List.of(
                new Token(TokenType.NUMBER, "5", null, 1),
                new Token(TokenType.EQUALS, "=", null, 1),
                new Token(TokenType.NUMBER, "10", null, 1)
        );

        Statement.Assignment assignment = assignmentStatementParser.parse(tokens);

        assertNull(assignment);
    }

    @Test
    public void testParseEmptyTokens() {
        List<Token> tokens = List.of();

        Statement.Assignment assignment = assignmentStatementParser.parse(tokens);

        assertNull(assignment);
    }

    @Test
    public void testParseIncompleteAssignment() {
        List<Token> tokens = List.of(
                new Token(TokenType.IDENTIFIER, "x", null, 1),
                new Token(TokenType.EQUALS, "=", null, 1)
        );

        Statement.Assignment assignment = assignmentStatementParser.parse(tokens);

        assertNull(assignment);
    }
}