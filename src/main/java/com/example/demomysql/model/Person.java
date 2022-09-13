package com.example.demomysql.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Person {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String dob;
}
