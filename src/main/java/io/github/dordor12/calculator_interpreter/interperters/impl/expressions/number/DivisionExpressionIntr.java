package io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number;

import java.util.function.BiFunction;

import org.springframework.stereotype.Component;

import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Value;
import io.github.dordor12.calculator_interpreter.dtos.Expression.BinaryOperator;
import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;

@Component
public class DivisionExpressionIntr extends NumberExprIntr {
    @Override
    public Operator getInterpOperator() {
        return Operator.BINARY_DIVISION;
    }

    @Override
    public Value interpert(Expression expression, BiFunction<Expression, CalcTypes, Value> recusiveInterp) {
        var binary = (BinaryOperator) expression;
        var left = (float) recusiveInterp.apply(binary.getLeft(), getInterperterType()).getValue();
        var right = (float) recusiveInterp.apply(binary.getRight(), getInterperterType()).getValue();
        return new Value(CalcTypes.NUMBER, left / right);
    }    
}