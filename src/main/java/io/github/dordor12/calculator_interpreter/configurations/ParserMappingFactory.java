package io.github.dordor12.calculator_interpreter.configurations;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;
import io.github.dordor12.calculator_interpreter.dtos.enums.OperatorType;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;

@Configuration
public class ParserMappingFactory {
     @Bean("binaryOperatorsMapping")
    public Map<TokenType, Operator> getBinaryOperatorsMapping() {
        return Arrays.stream(Operator.values())
            .filter(op -> op.getOperatorType() == OperatorType.BinaryOperator)
            .collect(Collectors.toMap(Operator::getTokenType, Function.identity()));
    }
    @Bean("unaryOperatorsMapping")
    public Map<TokenType, Operator> getUnaryOperatorsMapping() {
        return Arrays.stream(Operator.values())
            .filter(op -> op.getOperatorType() == OperatorType.UnaryOperator)
            .collect(Collectors.toMap(Operator::getTokenType, Function.identity()));
    }

    @Bean("postfixUnaryOperatorsMapping")
    public Map<TokenType, Operator> getPostfixUnaryOperatorsMapping() {
        return Arrays.stream(Operator.values())
            .filter(op -> op.getOperatorType() == OperatorType.PostfixUnaryOperator)
            .collect(Collectors.toMap(Operator::getTokenType, Function.identity()));
    }

}
