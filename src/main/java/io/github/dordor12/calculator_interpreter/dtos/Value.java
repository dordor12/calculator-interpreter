package io.github.dordor12.calculator_interpreter.dtos;

import io.github.dordor12.calculator_interpreter.dtos.enums.CalcTypes;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Value {
    private CalcTypes type;
    private Object value;

    @Override
    public String toString() {
        return value.toString();
    }
}
