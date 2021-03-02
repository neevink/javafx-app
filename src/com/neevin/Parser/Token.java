package com.neevin.Parser;

/**
 * Токен, валидное слово
 */
public class Token {
    /**
     * Строка, подходящая под паттерн
     */
    public final String object;
    /**
     * Опреденённый тип строки
     */
    public final TokenType type;

    public Token(String obj, TokenType t) {
        this.object = obj;
        this.type = t;
    }
}
