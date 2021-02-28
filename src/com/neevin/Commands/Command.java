package com.neevin.Commands;

import com.neevin.Parser.Token;

import java.util.AbstractList;

/**
 * Базовый интерфейс для всех команд
 */
public interface Command{
    String getName();
    String getDescription();
    void execute(AbstractList<Token> tokens) throws Exception;
}
