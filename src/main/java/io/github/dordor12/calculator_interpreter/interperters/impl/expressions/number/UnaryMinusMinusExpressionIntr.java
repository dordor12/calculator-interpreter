package io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number;

import java.util.function.BiFunction;

import org.springframework.stereotype.Component;

import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import io.github.dordor12.calculator_interpreter.Environment;
import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Value;
import io.github.dordor12.calculator_interpreter.dtos.Expression.UnaryOperator;
import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UnaryMinusMinusExpressionIntr extends NumberExprIntr {
    private Environment environment;

    @Override
    public Operator getInterpOperator() {
        return Operator.UNARY_MINUS_MINUS;
    }

    @Override
    public Value interpert(Expression expression, BiFunction<Expression, CalcTypes, Value> recusiveInterp) {
        var unary = (UnaryOperator) expression;
        var operand = (float) recusiveInterp.apply(unary.getExpression(), getInterperterType()).getValue();
        operand = operand - 1;
        var value = new Value(CalcTypes.NUMBER, operand);
        if (unary.getExpression() instanceof Expression.Variable) {
            var variable = (Expression.Variable) unary.getExpression();
            environment.setVariable(variable.getName(), value);
        } else {
            throw new RuntimeException("can't decrement non variable");
        }
        return new Value(CalcTypes.NUMBER, operand);
    }    
}