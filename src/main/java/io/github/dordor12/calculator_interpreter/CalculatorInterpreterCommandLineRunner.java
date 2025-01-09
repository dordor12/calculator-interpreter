package io.github.dordor12.calculator_interpreter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.dordor12.calculator_interpreter.io.IOHandler;
import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class CalculatorInterpreterCommandLineRunner implements CommandLineRunner {
    private final IOHandler ioHandler;
    private final Environment environment;
    private final Tokenizer tokenizer;
    private final Parser parser;
    private final Interperter interperter;



    @Override
    public void run(String... args) throws Exception {
        var code = ioHandler.getCode(args);
        var tokens = tokenizer.tokenize(code);
        var statements = parser.parse(tokens);
        interperter.interpret(statements);
        ioHandler.displayVariables(environment.getVariables());
    }

    
}
