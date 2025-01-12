package io.github.dordor12.calculator_interpreter.parsers;

import java.util.List;

import io.github.dordor12.calculator_interpreter.dtos.Statement;
import io.github.dordor12.calculator_interpreter.dtos.Token;

public interface StatementParser {
    public Statement parse(List<Token> tokens);
}
