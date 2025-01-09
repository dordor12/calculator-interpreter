package io.github.dordor12.calculator_interpreter.interperters.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import io.github.dordor12.calculator_interpreter.Environment;
import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Value;
import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;
import io.github.dordor12.calculator_interpreter.interperters.OperatorExpressionInterpeter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExpressionInterpeter {
    private final Environment environment;
    private final Map<CalcTypes, Map<Operator, OperatorExpressionInterpeter>> interperterMap;


    public Value interpert(Expression expression) {
        return interpert(expression, inferType(expression));
    }

    private Value interpert(Expression expression, CalcTypes type) {
        if (expression instanceof Expression.ExprOperator) {
            var exprOperator = (Expression.ExprOperator) expression;
            var operator = exprOperator.getOperator();
            if (!interperterMap.containsKey(type) || !interperterMap.get(type).containsKey(operator)) {
                throw new IllegalArgumentException("Interperter not found for operator: " + operator + " type: " + type);
            }
            var interperter = interperterMap.get(type).get(operator);
            return interperter.interpert(expression, this::interpert);
        } else if (expression instanceof Expression.Literal) {
            return ((Expression.Literal) expression).getValue();
        } else if (expression instanceof Expression.Variable) {
            var variableName = ((Expression.Variable) expression).getName();
            if (!environment.hasVariable(variableName)) throw new IllegalArgumentException("Variable is not defind variableName: " + variableName);
            return environment.getVariable(variableName); 
        } else {
            throw new IllegalArgumentException("Unknown expression type");
        }
    }

    private CalcTypes inferType(Expression expression) {
        return inferType(expression, null);
    }

    private CalcTypes inferType(Expression expression, CalcTypes currentType) {
        if (expression instanceof Expression.UnaryOperator) {
            var unaryOperator = (Expression.UnaryOperator) expression;
            return getType(inferType(unaryOperator.getExpression(), currentType), currentType);
        } else if (expression instanceof Expression.BinaryOperator) {
            var binaryOperator = (Expression.BinaryOperator) expression;
            currentType = getType(inferType(binaryOperator.getLeft(), currentType), currentType);
            return getType(inferType(binaryOperator.getRight(), currentType), currentType);
        } else if (expression instanceof Expression.PostfixUnary) {
            var postfixUnary = (Expression.PostfixUnary) expression;
            return getType(inferType(postfixUnary.getExpression(), currentType), currentType);
        }
         else if (expression instanceof Expression.Literal) {
            var literal = (Expression.Literal) expression;
            return getType(literal.getValue().getType(), currentType);
        } else if (expression instanceof Expression.Variable) {
            var variableName = ((Expression.Variable) expression).getName();
            if (!environment.hasVariable(variableName)) throw new IllegalArgumentException("Variable is not defind variableName: " + variableName);
            return getType(environment.getVariable(variableName).getType(), currentType);
        } else {
            throw new IllegalArgumentException("Unknown expression type");
        }
    }

    private CalcTypes getType(CalcTypes newType, CalcTypes currentType) {
        if (currentType == null) return newType;
        if (currentType != newType) throw new IllegalArgumentException("Cannot infer type");
        return currentType;
    }
}
