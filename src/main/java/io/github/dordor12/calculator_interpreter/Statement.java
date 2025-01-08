package io.github.dordor12.calculator_interpreter;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public abstract class Statement {
    private List<Token> tokens;

    @Getter
    public static class Assignment extends Statement {
        private Token variable;
        private Expression expression;
        
        public Assignment(List<Token> tokens, Token variable, Expression expression) {
            super(tokens);
            this.expression = expression;
            this.variable = variable;
        }

        public static class Plus extends Assignment { public Plus(List<Token> tokens, Token variable, Expression expression) { super(tokens, variable, expression); } }

        public static class Minus extends Assignment {  public Minus(List<Token> tokens, Token variable, Expression expression) { super(tokens, variable, expression); } }
    
    }
}
