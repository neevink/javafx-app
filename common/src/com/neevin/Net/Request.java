package com.neevin.Net;

import java.io.Serializable;

/**
 * Запрос на сервер
 * @param <T> тип содержимого запроса
 */
public class Request<T>  implements Serializable {
    /**
     * Название команды, которую нужно выполнить
     */
    public final String command;
    /**
     * Передаваемая в запросе сущность
     */
    public final T entity;

    public final String userLogin;
    public final String userPassword;

    public Request(String command, T entity, String userLogin, String userPassword) {
        this.command = command;
        this.entity = entity;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }
}
