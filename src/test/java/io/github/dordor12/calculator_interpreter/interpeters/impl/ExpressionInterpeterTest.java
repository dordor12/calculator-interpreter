package io.github.dordor12.calculator_interpreter.interpeters.impl;

import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;import io.github.dordor12.calculator_interpreter.Environment;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;
import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Value;
import io.github.dordor12.calculator_interpreter.interperters.OperatorExpressionInterpeter;
import io.github.dordor12.calculator_interpreter.interperters.impl.ExpressionInterpeter;
import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



public class ExpressionInterpeterTest {

    // private Environment environment;
    // private Map<CalcTypes, Map<Operator, OperatorExpressionInterpeter>> interperterMap;
    // private ExpressionInterpeter expressionInterpeter;

    // @BeforeEach
    // public void setUp() {
    //     environment = mock(Environment.class);
    //     interperterMap = mock(Map.class);
    //     expressionInterpeter = new ExpressionInterpeter(environment, interperterMap);
    // }

    // @Test
    // public void testInterpertLiteral() {
    //     Expression.Literal literal = new Expression.Literal(new Value(CalcTypes.NUMBER, 5.0));
    //     Value result = expressionInterpeter.interpert(literal);
    //     assertEquals(5.0, result.getValue());
    // }

    // @Test
    // public void testInterpertVariable() {
    //     when(environment.hasVariable("x")).thenReturn(true);
    //     when(environment.getVariable("x")).thenReturn(new Value(CalcTypes.NUMBER, 10.0));

    //     Expression.Variable variable = new Expression.Variable("x");
    //     Value result = expressionInterpeter.interpert(variable);
    //     assertEquals(10.0, result.getValue());
    // }

    // @Test
    // public void testInterpertUnaryOperator() {
    //     Expression.Literal literal = new Expression.Literal(new Value(CalcTypes.NUMBER, 5.0));
    //     Expression.UnaryOperator unaryOperator = new Expression.UnaryOperator(TokenType.MINUS, Operator.UNARY_MINUS, literal);

    //     OperatorExpressionInterpeter operatorExpressionInterpeter = mock(OperatorExpressionInterpeter.class);
    //     when(interperterMap.containsKey(CalcTypes.NUMBER)).thenReturn(true);
    //     when(interperterMap.get(CalcTypes.NUMBER)).thenReturn(Map.of(Operator.UNARY_MINUS, operatorExpressionInterpeter));
    //     when(operatorExpressionInterpeter.interpert(any(), any())).thenReturn(new Value(CalcTypes.NUMBER, 5.0));

    //     Value result = expressionInterpeter.interpert(unaryOperator);
    //     assertEquals(5.0, result.getValue());
    // }

    // @Test
    // public void testInterpertBinaryOperator() {
    //     Expression.Literal left = new Expression.Literal(new Value(CalcTypes.NUMBER, 5.0));
    //     Expression.Literal right = new Expression.Literal(new Value(CalcTypes.NUMBER, 3.0));
    //     Expression.BinaryOperator binaryOperator = new Expression.BinaryOperator(Operator.PLUS, left, right);

    //     OperatorExpressionInterpeter operatorExpressionInterpeter = mock(OperatorExpressionInterpeter.class);
    //     when(interperterMap.containsKey(CalcTypes.NUMBER)).thenReturn(true);
    //     when(interperterMap.get(CalcTypes.NUMBER)).thenReturn(Map.of(Operator.PLUS, operatorExpressionInterpeter));
    //     when(operatorExpressionInterpeter.interpert(any(), any())).thenReturn(new Value(CalcTypes.NUMBER, 8.0));

    //     Value result = expressionInterpeter.interpert(binaryOperator);
    //     assertEquals(8.0, result.getValue());
    // }

    // @Test
    // public void testInterpertComplexExpression() {
    //     Expression.Literal literal1 = new Expression.Literal(new Value(CalcTypes.NUMBER, 5.0));
    //     Expression.Literal literal2 = new Expression.Literal(new Value(CalcTypes.NUMBER, 3.0));
    //     Expression.BinaryOperator binaryOperator = new Expression.BinaryOperator(Operator.PLUS, literal1, literal2);
    //     Expression.UnaryOperator unaryOperator = new Expression.UnaryOperator(Operator.MINUS, binaryOperator);

    //     OperatorExpressionInterpeter plusInterpeter = mock(OperatorExpressionInterpeter.class);
    //     OperatorExpressionInterpeter minusInterpeter = mock(OperatorExpressionInterpeter.class);
    //     when(interperterMap.containsKey(CalcTypes.NUMBER)).thenReturn(true);
    //     when(interperterMap.get(CalcTypes.NUMBER)).thenReturn(Map.of(Operator.PLUS, plusInterpeter, Operator.MINUS, minusInterpeter));
    //     when(plusInterpeter.interpert(any(), any())).thenReturn(new Value(CalcTypes.NUMBER, 8.0));
    //     when(minusInterpeter.interpert(any(), any())).thenReturn(new Value(CalcTypes.NUMBER, -8.0));

    //     Value result = expressionInterpeter.interpert(unaryOperator);
    //     assertEquals(-8.0, result.getValue());
    // }
}