package io.github.dordor12.calculator_interpreter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Configuration
public class TokenMappingFactory {

    @Bean
    public Map<String, TokenType> createTokenMapping() {
        return Arrays.stream(TokenType.values())
                .collect(Collectors.toMap(TokenType::getValue, Function.identity()));
    }

    @Bean
    public RecursiveTokenMap createRecursiveTokenMap() {
        return getSortedTokenTypes(TokenType.values());
    }

    protected RecursiveTokenMap getSortedTokenTypes(TokenType[] tokenTypes) {
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
