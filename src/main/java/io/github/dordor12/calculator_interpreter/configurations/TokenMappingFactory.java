package io.github.dordor12.calculator_interpreter.configurations;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.dordor12.calculator_interpreter.dtos.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Configuration
public class TokenMappingFactory {

    @Bean
    public RecursiveTokenMap createRecursiveTokenMap() {
        return getSortedTokenTypes(TokenType.values());
    }

    public RecursiveTokenMap getSortedTokenTypes(TokenType[] tokenTypes) {
        RecursiveTokenMap map = new RecursiveTokenMap(new HashMap<>(), null);
        for (TokenType tokenType : tokenTypes) {
            var token = tokenType.getValue();
            if (token == null) continue;
            RecursiveTokenMap innerMap = map;
            for (int i = 0; i < token.length(); i++) {
                char currentChar = token.charAt(i);
                if (i == token.length() - 1) {
                    innerMap.getMap().computeIfAbsent(currentChar, k -> new RecursiveTokenMap(new HashMap<>(), null))
                            .setTokenType(tokenType);
                } else {
                    innerMap = innerMap.getMap().computeIfAbsent(currentChar, k -> new RecursiveTokenMap(new HashMap<>(), null));
                }
            }
        }
        return map;
    }

    @Getter
    @AllArgsConstructor
    @Setter
    public class RecursiveTokenMap {
        private Map<Character, RecursiveTokenMap> map;
        private TokenType tokenType;
    }
}
