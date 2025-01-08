package io.github.dordor12.calculator_interpreter;

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
