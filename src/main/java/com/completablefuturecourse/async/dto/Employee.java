package com.completablefuturecourse.async.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private String newJoiner;
    private String learningPending;
    private String emailID;
    private int salary;
    private int rating;


}