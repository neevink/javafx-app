package com.neevin.Parser;

public class Token {
    public String object;
    public TokenType type;

    public Token(String obj, TokenType t) {
        this.object = obj;
        this.type = t;
    }
}
