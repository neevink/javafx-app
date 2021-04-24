package com.neevin.DataModels;

import java.io.Serializable;

public class Account implements Serializable {
    public final String accountName;
    public final String password;

    public Account(String accountName, String password){
        this.accountName = accountName;
        this.password = password;
    }
}
