package io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number;

import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import io.github.dordor12.calculator_interpreter.interperters.OperatorExpressionInterpeter;

public abstract class NumberExprIntr implements OperatorExpressionInterpeter {
    @Override
    public CalcTypes getInterperterType() {
        return CalcTypes.NUMBER;
    }
    
}
