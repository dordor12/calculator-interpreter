package io.github.dordor12.calculator_interpreter.interperters.impl.statments;

import io.github.dordor12.calculator_interpreter.Environment;
import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Statement;
import io.github.dordor12.calculator_interpreter.dtos.Value;
import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;
import io.github.dordor12.calculator_interpreter.dtos.enums.StatementsTypes;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;
import io.github.dordor12.calculator_interpreter.interperters.impl.ExpressionInterpeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MinusAssignmentStatementIntr extends AssignmentStatementIntr {
    public MinusAssignmentStatementIntr(@Autowired Environment environment, @Autowired ExpressionInterpeter expressionInterpeter) {
        super(environment, expressionInterpeter);
    }

    @Override
    public Value getExpression(Statement.Assignment assignment) {
        return super.expressionInterpeter.interpert(new Expression.BinaryOperator(
            TokenType.MINUS, 
            Operator.BINARY_SUBTRACTION, 
            new Expression.Variable(assignment.getVariable().getStringValue()), assignment.getExpression())
        );
    }

    @Override
    public StatementsTypes getInterperterType() {
        return StatementsTypes.MINUS_ASSIGNMENT;
    }
}