package io.github.dordor12.calculator_interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;

import org.springframework.stereotype.Service;

import io.github.dordor12.calculator_interpreter.dtos.Value;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class Environment {
    private Map<String, Value> variables = new HashMap<>();

    public void setVariable(String name, Value value) {
        variables.put(name, value);
    }

    public Value getVariable(String name) {
        return variables.get(name);
    }

    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }

    public List<Entry<String, Value>> getVariables() {
        return variables.entrySet().stream().toList();
    }
}