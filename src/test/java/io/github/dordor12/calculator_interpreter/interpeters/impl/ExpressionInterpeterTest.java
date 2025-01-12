package io.github.dordor12.calculator_interpreter.interpeters.impl;

import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import io.github.dordor12.calculator_interpreter.Environment;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;
import io.github.dordor12.calculator_interpreter.dtos.Expression;
import io.github.dordor12.calculator_interpreter.dtos.Value;
import io.github.dordor12.calculator_interpreter.interperters.OperatorExpressionInterpeter;
import io.github.dordor12.calculator_interpreter.interperters.impl.ExpressionInterpeter;
import io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number.AddExpressionIntr;
import io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number.DivisionExpressionIntr;
import io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number.MultiExpressionIntr;
import io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number.PostfixMinusMinusExpressionIntr;
import io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number.PostfixPlusPlusExpressionIntr;
import io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number.SubExpressionIntr;
import io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number.UnaryMinusExpressionIntr;
import io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number.UnaryMinusMinusExpressionIntr;
import io.github.dordor12.calculator_interpreter.interperters.impl.expressions.number.UnaryPlusPlusExpressionIntr;
import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


public class ExpressionInterpeterTest {
    private Environment environment;
    private Map<CalcTypes, Map<Operator, OperatorExpressionInterpeter>> interperterMap;
    private ExpressionInterpeter expressionInterpeter;

    @BeforeEach
    public void setUp() {
        environment = new Environment();
        interperterMap = Map.of(
        CalcTypes.NUMBER, new HashMap<Operator, OperatorExpressionInterpeter>() {{
            put(Operator.BINARY_ADDITION, new AddExpressionIntr());
            put(Operator.BINARY_SUBTRACTION, new SubExpressionIntr());
            put(Operator.BINARY_MULTIPLICATION, new MultiExpressionIntr());
            put(Operator.BINARY_DIVISION, new DivisionExpressionIntr());
            put(Operator.UNARY_MINUS, new UnaryMinusExpressionIntr());
            put(Operator.UNARY_MINUS_MINUS, new UnaryMinusMinusExpressionIntr(environment));
            put(Operator.UNARY_PLUS_PLUS, new UnaryPlusPlusExpressionIntr(environment));
            put(Operator.POSTFIX_MINUS_MINUS, new PostfixMinusMinusExpressionIntr(environment));
            put(Operator.POSTFIX_PLUS_PLUS, new PostfixPlusPlusExpressionIntr(environment));
        }}
    );
        expressionInterpeter = new ExpressionInterpeter(environment, interperterMap);
    }

    @Test //(i + j) * 2 - (k / 3) + m-- + --f
    public void testComplexExprssion() {
        environment.setVariable("i", new Value(CalcTypes.NUMBER, (float)10.0));
        environment.setVariable("j", new Value(CalcTypes.NUMBER, (float)20.0));
        environment.setVariable("k", new Value(CalcTypes.NUMBER, (float)30.0));
        environment.setVariable("m", new Value(CalcTypes.NUMBER, (float)40.0));
        environment.setVariable("f", new Value(CalcTypes.NUMBER, (float)50.0));

        Expression expression = new Expression.BinaryOperator(
            TokenType.PLUS, Operator.BINARY_ADDITION,
            new Expression.BinaryOperator(
                TokenType.PLUS, Operator.BINARY_ADDITION,
                new Expression.BinaryOperator(
                    TokenType.MINUS, Operator.BINARY_SUBTRACTION,
                    new Expression.BinaryOperator(
                        TokenType.MULTIPLY, Operator.BINARY_MULTIPLICATION,
                        new Expression.BinaryOperator(
                            TokenType.PLUS, Operator.BINARY_ADDITION,
                            new Expression.Variable("i"),
                            new Expression.Variable("j")
                        ),
                        new Expression.Literal(new Value(CalcTypes.NUMBER, (float)2.0))
                    ),
                    new Expression.BinaryOperator(
                        TokenType.DIVIDE, Operator.BINARY_DIVISION,
                        new Expression.Variable("k"),
                        new Expression.Literal(new Value(CalcTypes.NUMBER, (float)3.0))
                    )
                ),
                new Expression.PostfixUnary(
                    TokenType.MINUS_MINUS, Operator.POSTFIX_MINUS_MINUS,
                    new Expression.Variable("m")
                )
            ),
            new Expression.UnaryOperator(
                TokenType.MINUS_MINUS, Operator.UNARY_MINUS_MINUS,
                new Expression.Variable("f")
            )
        );

        assertEquals((float)139.0, expressionInterpeter.interpert(expression).getValue());
    }

    @Test // -(i + --j) * 2 - --j
    public void testAnotherComplexExpression() {
        environment.setVariable("i", new Value(CalcTypes.NUMBER, (float)10.0));
        environment.setVariable("j", new Value(CalcTypes.NUMBER, (float)20.0));
        
        Expression expression = new Expression.BinaryOperator(
            TokenType.MINUS, Operator.BINARY_SUBTRACTION,
            new Expression.UnaryOperator(
                TokenType.MINUS, Operator.UNARY_MINUS,
                new Expression.BinaryOperator(
                    TokenType.MULTIPLY, Operator.BINARY_MULTIPLICATION,
                    new Expression.BinaryOperator(
                        TokenType.PLUS, Operator.BINARY_ADDITION,
                        new Expression.Variable("i"),
                        new Expression.UnaryOperator(
                            TokenType.MINUS_MINUS, Operator.UNARY_MINUS_MINUS,
                            new Expression.Variable("j")
                        )
                    ),
                    new Expression.Literal(new Value(CalcTypes.NUMBER, (float)2.0))
                )
            ),
            new Expression.UnaryOperator(
                TokenType.MINUS_MINUS, Operator.UNARY_MINUS_MINUS,
                new Expression.Variable("j")
            )
        );

        assertEquals((float)-76.0, expressionInterpeter.interpert(expression).getValue());
    }

    @Test
    public void testNonValidOperator() {
        environment.setVariable("i", new Value(CalcTypes.NUMBER, (float)10.0));
        environment.setVariable("j", new Value(CalcTypes.NUMBER, (float)20.0));
        interperterMap.get(CalcTypes.NUMBER).remove(Operator.UNARY_MINUS);
        
        Expression expression = new Expression.BinaryOperator(
            TokenType.MINUS, Operator.BINARY_SUBTRACTION,
            new Expression.UnaryOperator(
                TokenType.MINUS, Operator.UNARY_MINUS,
                new Expression.BinaryOperator(
                    TokenType.MULTIPLY, Operator.BINARY_MULTIPLICATION,
                    new Expression.BinaryOperator(
                        TokenType.PLUS, Operator.BINARY_ADDITION,
                        new Expression.Variable("i"),
                        new Expression.UnaryOperator(
                            TokenType.MINUS_MINUS, Operator.UNARY_MINUS_MINUS,
                            new Expression.Variable("j")
                        )
                    ),
                    new Expression.Literal(new Value(CalcTypes.NUMBER, (float)2.0))
                )
            ),
            new Expression.UnaryOperator(
                TokenType.MINUS_MINUS, Operator.UNARY_MINUS_MINUS,
                new Expression.Variable("j")
            )
        );

        assertThrows(IllegalArgumentException.class, () -> expressionInterpeter.interpert(expression));
    }

    @Test
    public void testUnknownExpressionType() {
        final class UnknownExpression extends Expression {
            public UnknownExpression() {
                super();
            }
        }
        Expression expression = new UnknownExpression();
        assertThrows(IllegalArgumentException.class, () -> expressionInterpeter.interpert(expression));
    }

    @Test
    public void testNonExitsVar() {
        environment.setVariable("i", new Value(CalcTypes.NUMBER, (float)10.0));
        
        Expression expression = new Expression.BinaryOperator(
            TokenType.MINUS, Operator.BINARY_SUBTRACTION,
            new Expression.UnaryOperator(
                TokenType.MINUS, Operator.UNARY_MINUS,
                new Expression.BinaryOperator(
                    TokenType.MULTIPLY, Operator.BINARY_MULTIPLICATION,
                    new Expression.BinaryOperator(
                        TokenType.PLUS, Operator.BINARY_ADDITION,
                        new Expression.Variable("i"),
                        new Expression.UnaryOperator(
                            TokenType.MINUS_MINUS, Operator.UNARY_MINUS_MINUS,
                            new Expression.Variable("j")
                        )
                    ),
                    new Expression.Literal(new Value(CalcTypes.NUMBER, (float)2.0))
                )
            ),
            new Expression.UnaryOperator(
                TokenType.MINUS_MINUS, Operator.UNARY_MINUS_MINUS,
                new Expression.Variable("j")
            )
        );
        assertThrows(IllegalArgumentException.class, () -> expressionInterpeter.interpert(expression));

    }
}