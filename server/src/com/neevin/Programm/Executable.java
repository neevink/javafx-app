package com.neevin.Programm;

import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;

public interface Executable {
    CommandResult execute(Request<?> request);
}
