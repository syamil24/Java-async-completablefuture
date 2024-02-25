package com.completablefuturecourse.async;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.completablefuturecourse.async.dto.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RunAsyncDemo {

    public Void saveEmployees(File jsonFile) throws ExecutionException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();

        //below code will be executed by separate thread
        CompletableFuture<Void> runAsyncFuture =  CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try{
                    List<Employee> employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                    });
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    employees.stream().forEach(System.out::println);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return runAsyncFuture.get();
    }


    public Void saveEmployeesUsingLambda(File jsonFile) throws ExecutionException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();

        Executor executor = Executors.newFixedThreadPool(5); // custom thread
        //below code will be executed by separate thread
        CompletableFuture<Void> runAsyncFuture =  CompletableFuture.runAsync( () -> {
                try{
                    List<Employee> employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                    });
                    System.out.println("Thread from lamda: " + Thread.currentThread().getName());
                    employees.stream().forEach(System.out::println);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        , executor);

        return runAsyncFuture.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RunAsyncDemo runAsyncDemo = new RunAsyncDemo();
        runAsyncDemo.saveEmployees(new File("employees.json"));
        runAsyncDemo.saveEmployeesUsingLambda( new File("employees.json"));
    }

}