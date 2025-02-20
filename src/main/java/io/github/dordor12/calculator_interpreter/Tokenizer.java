package io.github.dordor12.calculator_interpreter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.dordor12.calculator_interpreter.configurations.TokenMappingFactory.RecursiveTokenMap;
import io.github.dordor12.calculator_interpreter.dtos.Token;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Tokenizer {
    private final RecursiveTokenMap tokenMap;

    public List<Token> tokenize(String source) {
        return new innerTokenizer(source).tokenize();
    }

    
    private class innerTokenizer {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    innerTokenizer(String source) {
        this.source = source;
    }

    List<Token> tokenize() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            var token = scanToken();
            if (token != null) {
                tokens.add(token);
            }
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private Token scanToken() {
        char c = advance();
        Token token = null;
        if (isReservedSymbol(c)) {
            var map = tokenMap.getMap().get(c);
            while (map.getMap().containsKey(peek())) {
                c = advance();
                map = map.getMap().get(c);
            }
            token = createToken(map.getTokenType());
        } else {
            if (isDigit(c)) {
                token = number();
            } else if (isAlpha(c)) {
                token = identifier();
            } else if (c == Character.LINE_SEPARATOR) {
                line++;
                token = createToken(TokenType.NEW_LINE);
            } else if (c == ' ' || c == '\n' ) { //skip spaces
                return token;
            }else {
                throw new IllegalArgumentException("Unexpected character. line: " + line +" char: " + c);
            }
        }
        return token;
    }

    private boolean isReservedSymbol(char c) {
        return tokenMap.getMap().containsKey(c);
    }

    private Token number() {
        while (isDigit(peek()))
            advance();

        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (isDigit(peek()))
                advance();
        }

        return createToken(TokenType.NUMBER,
                Float.parseFloat(source.substring(start, current)));
    }

    private Token identifier() {
        while (isAlphaNumeric(peek())) advance();
        return createToken(TokenType.IDENTIFIER);
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private char peek() {
        if (isAtEnd())
            return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length())
            return '\0';
        return source.charAt(current + 1);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    } 

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    private Token createToken(TokenType type) {
        return createToken(type, null);
    }

    private Token createToken(TokenType type, Object value) {
        String text = source.substring(start, current);
        return new Token(type, text, value, line);
    }

}
}
