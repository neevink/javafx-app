package com.neevin.Programm;

import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;

/**
 * Базовый интерфейс для всех операций по управлению коллекцией
 */
public interface Executable {
    CommandResult execute(Request<?> request);
}
