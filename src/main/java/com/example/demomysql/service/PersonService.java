//service layer is to write the business logic
package com.example.demomysql.service;

import com.example.demomysql.model.Person;
import com.example.demomysql.repository.PersonRepository;
import com.example.demomysql.request.CreatePersonRequest;
import com.example.demomysql.util.BadPersonRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PersonService {
    private static Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    PersonRepository personRepository;

    public void createPerson(CreatePersonRequest createPersonRequest){
        Person person = createPersonRequest.to();
        if(person.getAge() == null){
            person.setAge(calculateAgeFromDOB(person.getDob()));
        }
        logger.info("Person: {}"+person);
        personRepository.createPerson(person);
    }

    public Integer calculateAgeFromDOB(String dob){
        if(dob == null){
            return null;
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate dobLocalDate = LocalDate.parse(dob);
        return Period.between(dobLocalDate,currentDate).getYears();
    }

    public Person getPersonById(int id) {
        return personRepository.getPersonById(id);
    }

    public List<Person> getPeople(){
        return personRepository.getPeople();
    }

    public Person deletePerson(int id) throws BadPersonRequestException {
         Person p = personRepository.getPersonById(id);
         boolean deleteResult = personRepository.deletePerson(id);
         if(deleteResult) {
             return p;
         }
         throw new BadPersonRequestException("Person with id :"+ id +" does not exist in the database");
    }
}
