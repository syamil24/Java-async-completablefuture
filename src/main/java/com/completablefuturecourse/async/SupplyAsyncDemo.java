package com.completablefuturecourse.async;

import com.completablefuturecourse.async.database.EmployeeDatabase;
import com.completablefuturecourse.async.dto.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SupplyAsyncDemo {

    public List<Employee> getEmployees() throws ExecutionException, InterruptedException {
            CompletableFuture<List<Employee>> employeeList = CompletableFuture.supplyAsync(() -> {
                System.out.println("Executed by: " + Thread.currentThread().getName());
                List<Employee> empList = null;
                try {
                    empList = EmployeeDatabase.fetchEmployeeList();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return empList;
            });
            return employeeList.get();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();
        List<Employee> employeeList = supplyAsyncDemo.getEmployees();
        employeeList.stream().forEach(System.out::println);
    }
}
