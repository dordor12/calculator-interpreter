package io.github.dordor12.calculator_interpreter.dtos;

import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Token {
    final TokenType type;
    final String stringValue;
    final Object value;
    final int line; // [location]
}
