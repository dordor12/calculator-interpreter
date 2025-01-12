package io.github.dordor12.calculator_interpreter;

import org.junit.jupiter.api.Test;

import io.github.dordor12.calculator_interpreter.configurations.TokenMappingFactory;
import io.github.dordor12.calculator_interpreter.configurations.TokenMappingFactory.RecursiveTokenMap;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;

import static org.junit.jupiter.api.Assertions.*;


public class TokenMappingFactoryTest {

    @Test
    public void nonIntercetedTokens() {
        TokenMappingFactory factory = new TokenMappingFactory();
        
        RecursiveTokenMap map = factory.getSortedTokenTypes(new TokenType[] {TokenType.PLUS, TokenType.MINUS});

        // Verify the structure of the map
        assertNotNull(map);
        assertNotNull(map.getMap());

        // Check some specific tokens
        RecursiveTokenMap plusMap = map.getMap().get('+');
        assertTrue(plusMap.getMap().isEmpty());
        assertEquals(TokenType.PLUS, plusMap.getTokenType());

        RecursiveTokenMap minusMap = map.getMap().get('-');
        assertTrue(minusMap.getMap().isEmpty());
        assertEquals(TokenType.MINUS, minusMap.getTokenType());
    }
    
    @Test
    public void intercetedTokens() {
        TokenMappingFactory factory = new TokenMappingFactory();
        
        RecursiveTokenMap map = factory.getSortedTokenTypes(new TokenType[] {TokenType.PLUS, TokenType.PLUS_EQUALS, TokenType.MINUS});
        
        // Verify the structure of the map
        assertNotNull(map);
        assertNotNull(map.getMap());
        
        // Check some specific tokens
        RecursiveTokenMap plusMap = map.getMap().get('+');
        assertFalse(plusMap.getMap().isEmpty());
        assertEquals(TokenType.PLUS, plusMap.getTokenType());
        assertTrue(plusMap.getMap().get('=').getMap().isEmpty());
        assertEquals(TokenType.PLUS_EQUALS, plusMap.getMap().get('=').getTokenType());

        
        RecursiveTokenMap minusMap = map.getMap().get('-');
        assertTrue(minusMap.getMap().isEmpty());
        assertEquals(TokenType.MINUS, minusMap.getTokenType());
    }

    @Test
    public void doubleIntercetedTokens() {
        TokenMappingFactory factory = new TokenMappingFactory();
        
        RecursiveTokenMap map = factory.getSortedTokenTypes(new TokenType[] {TokenType.PLUS, TokenType.PLUS_EQUALS,TokenType.PLUS_PLUS, TokenType.MINUS});
        
        // Verify the structure of the map
        assertNotNull(map);
        assertNotNull(map.getMap());
        
        // Check some specific tokens
        RecursiveTokenMap plusMap = map.getMap().get('+');
        assertFalse(plusMap.getMap().isEmpty());
        assertEquals(plusMap.getMap().size(), 2);
        assertEquals(TokenType.PLUS, plusMap.getTokenType());
        assertTrue(plusMap.getMap().get('=').getMap().isEmpty());
        assertEquals(TokenType.PLUS_EQUALS, plusMap.getMap().get('=').getTokenType());
        assertTrue(plusMap.getMap().get('+').getMap().isEmpty());
        assertEquals(TokenType.PLUS_PLUS, plusMap.getMap().get('+').getTokenType());

        
        RecursiveTokenMap minusMap = map.getMap().get('-');
        assertTrue(minusMap.getMap().isEmpty());
        assertEquals(TokenType.MINUS, minusMap.getTokenType());
    }

    @Test
    public void nonIntercetedTokensWithMultiCharToken() {
        TokenMappingFactory factory = new TokenMappingFactory();
        
        RecursiveTokenMap map = factory.getSortedTokenTypes(new TokenType[] {TokenType.PLUS_PLUS, TokenType.MINUS});

        // Verify the structure of the map
        assertNotNull(map);
        assertNotNull(map.getMap());

        // Check some specific tokens
        RecursiveTokenMap plusMap = map.getMap().get('+');
        assertNull(plusMap.getTokenType());
        RecursiveTokenMap plusPlusMap = plusMap.getMap().get('+');
        assertTrue(plusPlusMap.getMap().isEmpty());
        assertEquals(TokenType.PLUS_PLUS, plusPlusMap.getTokenType());

        RecursiveTokenMap minusMap = map.getMap().get('-');
        assertTrue(minusMap.getMap().isEmpty());
        assertEquals(TokenType.MINUS, minusMap.getTokenType());
    }
    
}