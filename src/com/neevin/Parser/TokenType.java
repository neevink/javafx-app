package com.neevin.Parser;

/**
 * Типы токенов
 */
public enum TokenType {

    /**
     * Любая строка без пробелов (возможно название команды)
     */
    WORD,
    /**
     * Любая строка в ковычках
     */
    STRING,
    /**
     * Целое число
     */
    INTEGER_NUMBER,
    /**
     * Дробное число
     */
    FLOAT_NUMBER,
}
