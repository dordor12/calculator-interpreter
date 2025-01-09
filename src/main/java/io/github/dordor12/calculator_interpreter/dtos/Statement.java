package io.github.dordor12.calculator_interpreter.dtos;

import java.util.List;

import io.github.dordor12.calculator_interpreter.dtos.enums.StatementsTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public abstract class Statement {
    private StatementsTypes type;
    private List<Token> tokens;

    @Getter
    public static class Assignment extends Statement {
        private Token variable;
        private Expression expression;
        
        public Assignment(List<Token> tokens, Token variable, Expression expression) {
            super(StatementsTypes.ASSIGNMENT, tokens);
            this.expression = expression;
            this.variable = variable;
        }
        public Assignment(StatementsTypes type, List<Token> tokens, Token variable, Expression expression) {
            super(type, tokens);
            this.expression = expression;
            this.variable = variable;
        }

        public static class Plus extends Assignment { public Plus(List<Token> tokens, Token variable, Expression expression) { super(StatementsTypes.PLUS_ASSIGNMENT,tokens, variable, expression); } }

        public static class Minus extends Assignment {  public Minus(List<Token> tokens, Token variable, Expression expression) { super(StatementsTypes.MINUS_ASSIGNMENT,tokens, variable, expression); } }
    
    }
}
