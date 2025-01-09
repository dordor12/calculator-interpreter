package io.github.dordor12.calculator_interpreter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;
import io.github.dordor12.calculator_interpreter.dtos.enums.OperatorType;
import io.github.dordor12.calculator_interpreter.dtos.enums.StatementsTypes;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;
import io.github.dordor12.calculator_interpreter.interperters.OperatorExpressionInterpeter;
import io.github.dordor12.calculator_interpreter.interperters.StatementInterpeter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Configuration
public class TokenMappingFactory {

    @Bean
    public Map<StatementsTypes, StatementInterpeter> getStatementInterpeters(List<StatementInterpeter> statementInterpeters) {
        return statementInterpeters.stream().collect(Collectors.toMap(StatementInterpeter::getInterperterType, Function.identity()));
    }

    @Bean
    public Map<CalcTypes, Map<Operator, OperatorExpressionInterpeter>> getExpressionIterMap(List<OperatorExpressionInterpeter> interpreters) {
        return interpreters.stream()
        .collect(Collectors
        .groupingBy(OperatorExpressionInterpeter::getInterperterType, 
                    Collectors.toMap(
                        OperatorExpressionInterpeter::getInterpOperator, 
                        Function.identity()
                    )
        ));
        }

    @Bean
    public Map<TokenType, CalcTypes> getTypeMapping() {
        return Arrays.stream(CalcTypes.values()).collect(Collectors.toMap(CalcTypes::getValue, Function.identity()));
    }

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

    @Bean
    public Map<String, TokenType> createTokenMapping() {
        return Arrays.stream(TokenType.values())
                .filter(tokenType -> tokenType.getValue() != null)
                .collect(Collectors.toMap(TokenType::getValue, Function.identity()));
    }

    @Bean
    public RecursiveTokenMap createRecursiveTokenMap() {
        return getSortedTokenTypes(TokenType.values());
    }

    protected RecursiveTokenMap getSortedTokenTypes(TokenType[] tokenTypes) {
        RecursiveTokenMap map = new RecursiveTokenMap(new HashMap<>(), null);
        for (TokenType tokenType : tokenTypes) {
            var token = tokenType.getValue();
            if (token == null) continue;
            RecursiveTokenMap innerMap = map;
            for (int i = 0; i < token.length(); i++) {
                char currentChar = token.charAt(i);
                if (i == token.length() - 1) {
                    innerMap.getMap().computeIfAbsent(currentChar, k -> new RecursiveTokenMap(new HashMap<>(), null))
                            .setTokenType(tokenType);
                } else {
                    innerMap = innerMap.getMap().computeIfAbsent(currentChar, k -> new RecursiveTokenMap(new HashMap<>(), null));
                }
            }
        }
        return map;
    }

    @Getter
    @AllArgsConstructor
    @Setter
    public class RecursiveTokenMap {
        private Map<Character, RecursiveTokenMap> map;
        private TokenType tokenType;
    }
}
