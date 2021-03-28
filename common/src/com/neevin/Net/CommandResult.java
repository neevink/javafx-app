package com.neevin.Net;

import java.io.Serializable;

public class CommandResult  implements Serializable {
    public final ResultStatus status;
    public final String message;

    public CommandResult(ResultStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
