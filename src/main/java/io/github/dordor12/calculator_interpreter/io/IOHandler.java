package io.github.dordor12.calculator_interpreter.io;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import io.github.dordor12.calculator_interpreter.dtos.Value;

public interface IOHandler {
    public String getCode(String[] args) throws IOException;
    public void displayVariables(List<Entry<String, Value>> variables);
}
