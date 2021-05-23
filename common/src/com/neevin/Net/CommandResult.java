package com.neevin.Net;

import java.io.Serializable;

/**
 * Результат выполнения запроса
 */
public class CommandResult<T> implements Serializable {
    /**
     * Статус выполнения
     */
    public final ResultStatus status;
    /**
     * Сообщение об удачном выполнении или об ошибке
     */
    public final String message;

    /**
     * Отправленная сущность
     */
    public T entity;

    public CommandResult(ResultStatus status, String message) {
        this.status = status;
        this.message = message;
        this.entity = null;
    }
}
