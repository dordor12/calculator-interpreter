package io.github.dordor12.calculator_interpreter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import io.github.dordor12.calculator_interpreter.dtos.Statement;
import io.github.dordor12.calculator_interpreter.dtos.Token;
import io.github.dordor12.calculator_interpreter.parsers.StatementParser;




public class ParserTest {

    @Mock
    private List<StatementParser> parsers;

    @InjectMocks
    private Parser parser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testParse_Success() {
        Token token1 = null;
        Token token2 = null;
        List<Token> tokens = Arrays.asList(token1, token2);

        Statement statement = new Statement.Assignment(Arrays.asList(token1, token2), null, null);
        StatementParser statementParser = mock(StatementParser.class);
        when(statementParser.parse(anyList())).thenReturn(statement);
        when(parsers.iterator()).thenReturn(Collections.singletonList(statementParser).iterator());

        List<Statement> result = parser.parse(tokens);

        assertEquals(1, result.size());
        assertEquals(statement, result.get(0));
    }

    @Test
    public void testParse_Failure() {
        Token token1 = new Token(null, null, CALLS_REAL_METHODS, 0);
        List<Token> tokens = Collections.singletonList(token1);

        StatementParser statementParser = mock(StatementParser.class);
        when(statementParser.parse(anyList())).thenReturn(null);
        when(parsers.iterator()).thenReturn(Collections.singletonList(statementParser).iterator());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            parser.parse(tokens);
        });

        assertTrue(exception.getMessage().contains("Could not parse tokens"));
    }
}