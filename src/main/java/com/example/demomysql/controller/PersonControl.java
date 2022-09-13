//controller has the list of API's that are present
package com.example.demomysql.controller;

import com.example.demomysql.model.Person;
import com.example.demomysql.request.CreatePersonRequest;
import com.example.demomysql.service.PersonService;
import com.example.demomysql.util.BadPersonRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonControl {

    private static Logger logger = LoggerFactory.getLogger(PersonControl.class);

    @Autowired
    PersonService personService;

    @PostMapping("/person")
    public void createPerson(@RequestBody @Valid CreatePersonRequest createPersonRequest){
        personService.createPerson(createPersonRequest);
    }

    @GetMapping("/person")
    public Person getPersonById(@RequestParam("id") int id){
        return personService.getPersonById(id);
    }

    @GetMapping("/person/all")
    public List<Person> getPeople(){
        return personService.getPeople();
    }

    @DeleteMapping("/person/delete/{id}")
    public Person deletePerson(@PathVariable("id") int id) throws BadPersonRequestException {
        return personService.deletePerson(id);
    }

}



//    @PostMapping("/person")
//    public ResponseEntity createPerson(@RequestBody @Valid CreatePersonRequest personRequest){
//        logger.info("Person Object: {}",personRequest);
//
//        MultiValueMap<String, String> headers = new HttpHeaders();
//        headers.add("sample_header","person request header");
//
//        ResponseEntity responseEntity = new ResponseEntity<>(personRequest, headers, HttpStatus.ACCEPTED);
//        return responseEntity;
//    }

//USING BUILDER
//        Person person = Person.builder()
//                .age(10)
//                .firstName("ABC")
//                .lastName("XYZ")
//                .build();
//
//        logger.info("Person : {}",person);
//        //--------------------
//
//        Person.PersonBuilder personBuilder = Person.builder()
//                .age(20)
//                .firstName("Honey");
//        Person p = lastNameCalculator(personBuilder);
//        logger.info("Person obj: {}",p);
//
//        person.setAge(20);
//--------------------------------------
//    public static Person lastNameCalculator(Person.PersonBuilder personBuilder){
//        return personBuilder.lastName("Bee").build();
//    }