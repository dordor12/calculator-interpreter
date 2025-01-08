package io.github.dordor12.calculator_interpreter.interperters;

import io.github.dordor12.calculator_interpreter.CalcTypes;
import io.github.dordor12.calculator_interpreter.Expression;
import io.github.dordor12.calculator_interpreter.TokenType;
import io.github.dordor12.calculator_interpreter.Value;

public interface ExpressionInterpeter {
    public Value interpert(Expression expression);
    public CalcTypes getInterperterType();
    public TokenType getInterperterTokenType();
}