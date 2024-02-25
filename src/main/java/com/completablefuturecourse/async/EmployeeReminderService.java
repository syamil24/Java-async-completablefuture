package com.completablefuturecourse.async;

import com.completablefuturecourse.async.database.EmployeeDatabase;
import com.completablefuturecourse.async.dto.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class EmployeeReminderService {

    public  CompletableFuture<Void> sendReminderToEmployee() {
        Executor executor = Executors.newFixedThreadPool(5);

        CompletableFuture<Void> sendReminderResult = CompletableFuture.supplyAsync(() -> {
            System.out.println("Current Thread for Getting Employee List: " + Thread.currentThread().getName());
            List<Employee> empList= null;
            try {
               empList = EmployeeDatabase.fetchEmployeeList();
           }
           catch (Exception e){
               e.printStackTrace();
           }
            return empList;
        }, executor).thenApplyAsync((employees) -> {
            System.out.println("Current Data:: " + employees);
            System.out.println("Current Thread for New Joiner: " + Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> "TRUE".equalsIgnoreCase(employee.getNewJoiner()))
                    .collect(Collectors.toList());
        }, executor).thenApplyAsync(employees -> {
            System.out.println("Current Data:: " + employees);
            System.out.println("Current Thread for Pending Learning: " + Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> "FALSE".equalsIgnoreCase(employee.getLearningPending()))
                    .collect(Collectors.toList());
        }, executor).thenApplyAsync(employees -> {
            System.out.println("Current Data:: " + employees);
            System.out.println("Current Thread for Get Emp Email: " + Thread.currentThread().getName());
            return employees.stream().map(Employee::getEmailID).collect(Collectors.toList());
        }, executor).thenAcceptAsync(emails -> {
            System.out.println("Current Data:: " + emails);
            System.out.println("Current Thread for Sending Email: " + Thread.currentThread().getName());
            emails.forEach(email -> sendEmail(email));
        }, executor);
        return sendReminderResult;
    }

    public static void sendEmail(String email){
        System.out.println("Sending email to:: " + email);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EmployeeReminderService service = new EmployeeReminderService();
        service.sendReminderToEmployee().get();
    }
}

/*
try {

        }
        catch (Exception e){
        e.printStackTrace();
        }*/
