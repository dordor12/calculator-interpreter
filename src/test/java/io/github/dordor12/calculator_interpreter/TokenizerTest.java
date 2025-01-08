package io.github.dordor12.calculator_interpreter;

import org.junit.jupiter.api.Test;

import io.github.dordor12.calculator_interpreter.TokenMappingFactory.RecursiveTokenMap;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;



public class TokenizerTest {
    private RecursiveTokenMap tokenMap = new TokenMappingFactory().createRecursiveTokenMap();

    @Test
    public void testScanTokensWithSingleCharacterTokens() {
        String source = "+-";
        Tokenizer tokenizer = new Tokenizer(source, tokenMap);

        List<Token> tokens = tokenizer.tokenize();

        assertEquals(3, tokens.size());
        assertEquals(TokenType.PLUS, tokens.get(0).getType());
        assertEquals(TokenType.MINUS, tokens.get(1).getType());
        assertEquals(TokenType.EOF, tokens.get(2).getType());
    }

    @Test
    public void testScanTokensWithMultiCharacterTokens() {
        String source = "++ --";
        Tokenizer tokenizer = new Tokenizer(source, tokenMap);

        List<Token> tokens = tokenizer.tokenize();

        assertEquals(3, tokens.size());
        assertEquals(TokenType.PLUS_PLUS, tokens.get(0).getType());
        assertEquals(TokenType.MINUS_MINUS, tokens.get(1).getType());
        assertEquals(TokenType.EOF, tokens.get(2).getType());
    }

    @Test
    public void testScanTokensWithNumbers() {
        String source = "123 45.67";
        Tokenizer tokenizer = new Tokenizer(source, tokenMap);

        List<Token> tokens = tokenizer.tokenize();

        assertEquals(3, tokens.size());
        assertEquals(TokenType.NUMBER, tokens.get(0).getType());
        assertEquals(123.0, tokens.get(0).getValue());
        assertEquals(TokenType.NUMBER, tokens.get(1).getType());
        assertEquals(45.67, tokens.get(1).getValue());
        assertEquals(TokenType.EOF, tokens.get(2).getType());
    }

    @Test
    public void testScanTokensWithIdentifiers() {
        String source = "abc def";
        Tokenizer tokenizer = new Tokenizer(source, tokenMap);

        List<Token> tokens = tokenizer.tokenize();

        assertEquals(3, tokens.size());
        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("abc", tokens.get(0).getStringValue());
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
        assertEquals("def", tokens.get(1).getStringValue());
        assertEquals(TokenType.EOF, tokens.get(2).getType());
    }

    @Test
    public void testScanTokensWithUnexpectedCharacter() {
        String source = "@";
        Tokenizer tokenizer = new Tokenizer(source, tokenMap);

        List<Token> tokens = tokenizer.tokenize();

        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.get(0).getType());
    }

    @Test
    public void testScanTokensWithMultilineString() {
        String source = "abc\ndef";
        Tokenizer tokenizer = new Tokenizer(source, tokenMap);

        List<Token> tokens = tokenizer.tokenize();

        assertEquals(4, tokens.size());
        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("abc", tokens.get(0).getStringValue());
        assertEquals(TokenType.NEW_LINE, tokens.get(1).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(2).getType());
        assertEquals("def", tokens.get(2).getStringValue());
        assertEquals(TokenType.EOF, tokens.get(3).getType());
    }

    @Test
    public void testScanTokensWithMixedContent() {
        String source = "abc 123 + - def 45.67";
        Tokenizer tokenizer = new Tokenizer(source, tokenMap);

        List<Token> tokens = tokenizer.tokenize();

        assertEquals(7, tokens.size());
        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("abc", tokens.get(0).getStringValue());
        assertEquals(TokenType.NUMBER, tokens.get(1).getType());
        assertEquals(123.0, tokens.get(1).getValue());
        assertEquals(TokenType.PLUS, tokens.get(2).getType());
        assertEquals(TokenType.MINUS, tokens.get(3).getType());
        assertEquals(TokenType.IDENTIFIER, tokens.get(4).getType());
        assertEquals("def", tokens.get(4).getStringValue());
        assertEquals(TokenType.NUMBER, tokens.get(5).getType());
        assertEquals(45.67, tokens.get(5).getValue());
        assertEquals(TokenType.EOF, tokens.get(6).getType());
    }

    @Test
    public void testScanTokensWithPlusPlusNearIdentifier() {
        String source = "abc++";
        Tokenizer tokenizer = new Tokenizer(source, tokenMap);

        List<Token> tokens = tokenizer.tokenize();

        assertEquals(3, tokens.size());
        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("abc", tokens.get(0).getStringValue());
        assertEquals(TokenType.PLUS_PLUS, tokens.get(1).getType());
        assertEquals(TokenType.EOF, tokens.get(2).getType());
    }
}