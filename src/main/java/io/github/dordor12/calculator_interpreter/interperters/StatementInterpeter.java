package io.github.dordor12.calculator_interpreter.interperters;

import io.github.dordor12.calculator_interpreter.dtos.Statement;
import io.github.dordor12.calculator_interpreter.dtos.enums.StatementsTypes;

public interface StatementInterpeter {
    public void interpert(Statement statment);
    public StatementsTypes getInterperterType();
}
