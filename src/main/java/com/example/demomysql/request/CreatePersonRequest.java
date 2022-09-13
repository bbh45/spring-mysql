package com.example.demomysql.request;


import com.example.demomysql.model.Person;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreatePersonRequest {
    @NotBlank(message = "First Name should not be empty")
    private String firstName;
    private String lastName;
    @NotBlank(message = "Date of Birth should not be empty")
    private String dob;

    public Person to(){
        return Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dob(dob).build();
    }
}
