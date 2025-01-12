package io.github.dordor12.calculator_interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.dordor12.calculator_interpreter.dtos.Statement;
import io.github.dordor12.calculator_interpreter.dtos.Token;
import io.github.dordor12.calculator_interpreter.parsers.StatementParser;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Parser {
    private final List<StatementParser> parsers;

    public List<Statement> parse(List<Token> tokens) {
        List<Statement> statments = new ArrayList<>();
        int tokenedParsed = 0;
        while (tokens.size() > tokenedParsed) {
            boolean parsed = false;
            for (StatementParser parser : parsers) {
                Statement statment = parser.parse(tokens.subList(tokenedParsed, tokens.size()));
                if (statment != null) {
                    statments.add(statment);
                    tokenedParsed += statment.getTokens().size() + 1; // remove newline
                    parsed = true;
                    break;
                }
            }
            if (!parsed) {
                throw new IllegalArgumentException("Could not parse tokens: " + tokens.subList(tokenedParsed, tokens.size()).stream().map(Token::getStringValue).collect(Collectors.joining(" ")));
            }
        }
        return statments;
    }
}
