package io.github.dordor12.calculator_interpreter.parsers.impl.statements;

import io.github.dordor12.calculator_interpreter.parsers.StatementParser;
import io.github.dordor12.calculator_interpreter.parsers.impl.ExpressionParser;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.dordor12.calculator_interpreter.Statement;
import io.github.dordor12.calculator_interpreter.Token;
import io.github.dordor12.calculator_interpreter.TokenType;

@Service
@AllArgsConstructor
public class AssignmentStatementParser implements StatementParser {
    private final ExpressionParser expressionParser;
    

	@Override
	public Statement.Assignment parse(List<Token> tokens) {
        var line = tokens.stream().takeWhile(t -> t.getType() != TokenType.NEW_LINE || t.getType() != TokenType.EOF).collect(Collectors.toList());
        if (line.size() > 3 && 
            line.get(0).getType() == TokenType.IDENTIFIER && 
            line.get(1).getType() == TokenType.EQUALS) {
            var expression = expressionParser.parse(line.subList(2, line.size()));
            return new Statement.Assignment(line, line.get(0), expression);
        }
        return null;
	}
}
