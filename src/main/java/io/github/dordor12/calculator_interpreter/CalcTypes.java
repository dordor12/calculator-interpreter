package io.github.dordor12.calculator_interpreter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CalcTypes {
    NUMBER(TokenType.NUMBER);

    public final TokenType value;
}
