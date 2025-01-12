package io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number;

import java.util.function.BiFunction;

import org.springframework.stereotype.Component;

import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import io.github.dordor12.calculator_interpreter.Environment;
import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Value;
import io.github.dordor12.calculator_interpreter.dtos.Expression.PostfixUnary;
import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PostfixPlusPlusExpressionIntr extends NumberExprIntr {
    private Environment environment;

    @Override
    public Operator getInterpOperator() {
        return Operator.POSTFIX_PLUS_PLUS;
    }

    @Override
    public Value interpert(Expression expression, BiFunction<Expression, CalcTypes, Value> recusiveInterp) {
        var unary = (PostfixUnary) expression;
        var operand = (float) recusiveInterp.apply(unary.getExpression(), getInterperterType()).getValue();
        var value = new Value(CalcTypes.NUMBER, operand);
        if (unary.getExpression() instanceof Expression.Variable) {
            var variable = (Expression.Variable) unary.getExpression();
            environment.setVariable(variable.getName(), new Value(CalcTypes.NUMBER, ++operand));
        } else {
            throw new RuntimeException("can't increment non variable");
        }
        return value;
    }    
}