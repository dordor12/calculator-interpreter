package io.github.dordor12.calculator_interpreter;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Value {
    private CalcTypes type;
    private Object value;
}
