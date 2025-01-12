package io.github.dordor12.calculator_interpreter.interperters.impl.statments;
import org.springframework.stereotype.Component;

import io.github.dordor12.calculator_interpreter.Environment;
import io.github.dordor12.calculator_interpreter.dtos.Statement;
import io.github.dordor12.calculator_interpreter.dtos.Value;
import io.github.dordor12.calculator_interpreter.dtos.enums.StatementsTypes;
import io.github.dordor12.calculator_interpreter.interperters.StatementInterpeter;
import io.github.dordor12.calculator_interpreter.interperters.impl.ExpressionInterpeter;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AssignmentStatementIntr implements StatementInterpeter {
    private Environment environment;
    protected ExpressionInterpeter expressionInterpeter;

    @Override
    public void interpert(Statement statment) {
        if (statment instanceof Statement.Assignment) {
            var assigment = (Statement.Assignment) statment;
            var value = getExpression(assigment);
            environment.setVariable(assigment.getVariable().getStringValue(), value);
        } else {
            throw new IllegalArgumentException("Unknown statement type");
        }
    }

    public Value getExpression(Statement.Assignment assigment) {
        return expressionInterpeter.interpert(assigment.getExpression());
    }

    @Override
    public StatementsTypes getInterperterType() {
        return StatementsTypes.ASSIGNMENT;
    }
    
}
