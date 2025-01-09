package io.github.dordor12.calculator_interpreter.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.dordor12.calculator_interpreter.dtos.Value;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class BasicIO implements IOHandler{

    public String getCode(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException("No file path provided in the arguments!");
        }

        String filePath = args[0];
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IllegalArgumentException("The file does not exist: " + filePath);
        }

        return Files.readString(path);

    }

    @Override
    public void displayVariables(List<Entry<String, Value>> variables) {
        String output = variables.stream()
            .map(entry -> entry.getKey() + " = " + entry.getValue().toString())
            .collect(Collectors.joining(","));

        System.out.println("(" + output + ")");
    }
    
}
