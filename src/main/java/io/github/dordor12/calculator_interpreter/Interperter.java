package io.github.dordor12.calculator_interpreter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.github.dordor12.calculator_interpreter.dtos.Statement;
import io.github.dordor12.calculator_interpreter.dtos.enums.StatementsTypes;
import io.github.dordor12.calculator_interpreter.interperters.StatementInterpeter;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class Interperter {
    private Map<StatementsTypes, StatementInterpeter> statementInterpeters;

    public void interpret(List<Statement> statements) {
        for (Statement statement : statements) {
            if (!statementInterpeters.containsKey(statement.getType())) {
                throw new IllegalArgumentException("No interpeter for statement type: " + statement.getType());
            }
            statementInterpeters.get(statement.getType()).interpert(statement);
        }
    }
}
