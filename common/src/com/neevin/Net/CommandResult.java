package com.neevin.Net;

import java.io.Serializable;

/**
 * Результат выполнения запроса
 */
public class CommandResult  implements Serializable {
    /**
     * Статус выполнения
     */
    public final ResultStatus status;
    /**
     * Результат или сообщение об ошибке
     */
    public final String message;

    public CommandResult(ResultStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
