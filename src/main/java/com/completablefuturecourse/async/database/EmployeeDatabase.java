package com.completablefuturecourse.async.database;

import com.completablefuturecourse.async.dto.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EmployeeDatabase {

    public static List<Employee> fetchEmployeeList() throws ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println("Thread from lamda: " + Thread.currentThread().getName());
            return mapper.readValue(new File("employees.json"), new TypeReference<List<Employee>>() {
            });
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
