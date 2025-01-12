package io.github.dordor12.calculator_interpreter.interperters;

import java.util.function.BiFunction;

import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Value;
import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;

public interface OperatorExpressionInterpeter {
    public Value interpert(Expression expression, BiFunction<Expression, CalcTypes, Value> recusiveInterp);
    public CalcTypes getInterperterType();
    public Operator getInterpOperator();
}