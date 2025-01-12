package io.github.dordor12.calculator_interpreter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import io.github.dordor12.calculator_interpreter.dtos.Statement;
import io.github.dordor12.calculator_interpreter.dtos.enums.StatementsTypes;
import io.github.dordor12.calculator_interpreter.interperters.StatementInterpeter;




public class InterperterTest {

    @Mock
    private StatementInterpeter mockStatementInterpeter;

    private Interperter interperter;
    private Map<StatementsTypes, StatementInterpeter> statementInterpeters;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        statementInterpeters = new HashMap<>();
        statementInterpeters.put(StatementsTypes.ASSIGNMENT, mockStatementInterpeter);
        interperter = new Interperter(statementInterpeters);
    }

    @Test
    public void testInterpret_withValidStatement() {
        Statement statement = new Statement.Assignment(null, null, null);

        interperter.interpret(Collections.singletonList(statement));

        verify(mockStatementInterpeter, times(1)).interpert(statement);
    }

    @Test
    public void testInterpret_withInvalidStatementType() {
        Statement statement = new Statement.Assignment(null, null, null);

        statement.setType(null);

        assertThrows(IllegalArgumentException.class, () -> {
            interperter.interpret(Collections.singletonList(statement));
        });
    }
}