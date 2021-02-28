package com.neevin.Parser;

import java.util.ArrayList;

public class Tokenizer {
    public static ArrayList<Token> tokenize(String input) throws Exception {
        ArrayList<Token> tokens = new ArrayList<Token>();

        for(int i = 0; i < input.length(); i++){
            // Если токен это строка
            if(input.charAt(i) == '"'){
                int start = ++i;
                while (i < input.length() && input.charAt(i) != '"'){
                    i++;
                }
                int end = i;

                if(end == input.length()){
                    throw new Exception("Ошибка парсинга строки. Отсутствует закрывающая ковычка.");
                }

                String inner = input.substring(start, end);
                Token token = new Token(inner, TokenType.STRING);

                tokens.add(token);
                continue;
            }

            // Если токен это число
            if(Character.isDigit(input.charAt(i)) || input.charAt(i) == '.' || input.charAt(i) == ',' || input.charAt(i) == '-' ){
                boolean wasCommaOrDot = input.charAt(i) == '.' || input.charAt(i) == ',';

                int start = i;
                i++;
                while (i < input.length() && ( Character.isDigit(input.charAt(i)) || input.charAt(i) == '.' || input.charAt(i) == ',' )){
                    if(input.charAt(i) == '.' || input.charAt(i) == ','){
                        if(wasCommaOrDot){
                            throw new Exception("Ошибка парсинга числа. В числе встречается более одной точки/запятой.");
                        }
                        wasCommaOrDot = true;
                    }
                    i++;
                }
                int end = i;

                String inner = input.substring(start, end);
                Token token = new Token(inner, wasCommaOrDot ? TokenType.FLOAT_NUMBER : TokenType.INTEGER_NUMBER);

                tokens.add(token);
                continue;
            }

            // Если токен это слово
            if(Character.isAlphabetic( input.charAt(i) )){
                int start = i;
                i++;
                while (i < input.length() && ( Character.isAlphabetic(input.charAt(i)) || input.charAt(i) == '_' )){
                    i++;
                }
                int end = i;

                String inner = input.substring(start, end);
                Token token = new Token(inner, TokenType.WORD);

                tokens.add(token);
                continue;
            }
        }

        return tokens;
    }
}
