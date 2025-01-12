package io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number;

import java.util.function.BiFunction;

import org.springframework.stereotype.Component;

import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Value;
import io.github.dordor12.calculator_interpreter.dtos.Expression.UnaryOperator;
import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;

@Component
public class UnaryMinusExpressionIntr extends NumberExprIntr {
    @Override
    public Operator getInterpOperator() {
        return Operator.UNARY_MINUS;
    }

    @Override
    public Value interpert(Expression expression, BiFunction<Expression, CalcTypes, Value> recusiveInterp) {
        var unary = (UnaryOperator) expression;
        var operand = (float) recusiveInterp.apply(unary.getExpression(), getInterperterType()).getValue();
        return new Value(CalcTypes.NUMBER, (float) (-1 * operand));
    }    
}