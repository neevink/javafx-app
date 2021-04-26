package com.neevin.DataModels;

import java.io.Serializable;

public class Account implements Serializable {
    public final String login;
    public final String password;

    public Account(String login, String password){
        this.login = login;
        this.password = password;
    }
}
