package com.neevin.Net;

import java.io.Serializable;

public class Request<T extends Serializable>  implements Serializable {
    public final String command;
    public final T entity;

    public Request(String command, T entity) {
        this.command = command;
        this.entity = entity;
    }
}
