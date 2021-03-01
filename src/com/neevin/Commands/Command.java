package com.neevin.Commands;

import com.neevin.Parser.Token;

import java.util.AbstractList;

/**
 * Базовый интерфейс для всех команд
 */
public interface Command{

    /**
     * @return Имя команды без аргументов
     */
    String getName();

    /**
     * @return Описание команды
     */
    String getDescription();

    /**
     * @param tokens Принимает список токенов - спарсенных аргументов данной команды
     * @throws Exception
     */
    void execute(AbstractList<Token> tokens) throws Exception;
}
