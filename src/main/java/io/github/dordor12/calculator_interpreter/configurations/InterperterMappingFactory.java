package io.github.dordor12.calculator_interpreter.configurations;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import io.github.dordor12.calculator_interpreter.dtos.enums.Operator;
import io.github.dordor12.calculator_interpreter.dtos.enums.StatementsTypes;
import io.github.dordor12.calculator_interpreter.interperters.OperatorExpressionInterpeter;
import io.github.dordor12.calculator_interpreter.interperters.StatementInterpeter;

@Configuration
public class InterperterMappingFactory {
    
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
}
