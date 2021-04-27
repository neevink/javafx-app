package com.neevin.Programm;

import com.neevin.Net.CommandResult;
import com.neevin.Net.Request;
import com.neevin.Net.ResultStatus;

import java.util.concurrent.Callable;

public class RequestHandler implements Callable<CommandResult> {
    ExecutionService executionService;
    Request<?> request;

    public RequestHandler(ExecutionService executionService, Request<?> request){
        this.executionService = executionService;
        this.request = request;
    }

    @Override
    public CommandResult call() throws Exception {
        CommandResult result = executionService.executeCommand(request);
        if (result.status == ResultStatus.OK) {
            System.out.println("Результат выполнения: успешно");
        } else {
            System.out.println("Результат выполнения: неуспешно");
        }
        System.out.println();
        return result;
    }
}
