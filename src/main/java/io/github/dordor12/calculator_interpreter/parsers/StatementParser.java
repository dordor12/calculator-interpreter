package io.github.dordor12.calculator_interpreter.parsers;

import java.util.List;

import io.github.dordor12.calculator_interpreter.Statement;
import io.github.dordor12.calculator_interpreter.Token;

public interface StatementParser {
    public Statement parse(List<Token> tokens);
}
