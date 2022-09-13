//also called as DA0 Layer(Data Access Object) - creates an object of model and saves it in the database
package com.example.demomysql.repository;

import com.example.demomysql.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    private static Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    private Connection connection;
    private PreparedStatement preparedStatement;

    /*
    * Insert/Update/Delete - ExecuteUpdate
    * Select - Execute Query
    */

    PersonRepository(Connection connection) throws SQLException{
        this.connection = connection;
        createTable();
        this.preparedStatement = connection.prepareStatement(
                "insert into person (firstName, lastName, age, dob) " +
                        "VALUES (?, ?, ?, ?)");
    }

    public void createPerson(Person p){
        PreparedStatement statement = null;
        try {
            //mapping java object to db
            preparedStatement.setString(1, p.getFirstName());
            preparedStatement.setString(2, p.getLastName());
            preparedStatement.setInt(3,p.getAge());
            preparedStatement.setString(4, p.getDob());
            int result = preparedStatement.executeUpdate();
            logger.info("Result on Inserting Person: {}",result == 1? true : false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Person getPersonById(int pId){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "Select * from Person where id = ? "
            );
            preparedStatement.setInt(1,pId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return getPersonFromResultSet(resultSet);
            }
        }catch (SQLException ex){
            ex.printStackTrace();;
        }
        return null;
    }

    public List<Person> getPeople(){
        List<Person> peopleList = new ArrayList<Person>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Person");

            //Mapping DB to java object - as below, long code
            //TO avoid it, ORM - Object Relation Mapping
            //JDBC - Protocol to connect to Relational Databases
            //JDBC Template - Dependency which allows you to map Java Object to DB Relation just like Hibernate, but Hibernate is more powerful
            //Hibernate - (does ORM) DOES OBJECT RELATION MAPPING, can only be used with relational Databases
            //Row Mapper - another dependency for object relation mapping

            while(resultSet.next()){
                Person p = getPersonFromResultSet(resultSet);
                peopleList.add(p);
            }
            logger.info("ResultSet : {}",peopleList);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return peopleList;
    }


    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists person (id int primary key AUTO_INCREMENT, firstName varchar(30), " +
                "lastName varchar(30), age int, dob varchar (12))");
    }

    private Person getPersonFromResultSet(ResultSet resultSet) throws SQLException {
        Person p;
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString(3);
        int age = resultSet.getInt(4);
        String dob = resultSet.getString(5);
        p = Person.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .dob(dob)
                .build();
        return p;
    }

    public boolean deletePerson(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from Person where id = ? "
            );
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            return result >= 1? true : false;
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }


    //    public void createPersonStatic(Person p){
//        try {
//              //Compilation and execution happends everytime
//            Statement statement = connection.createStatement();
//            int result = statement.executeUpdate("INSERT INTO Person(id, firstName, lastName, age, dob) VALUES (1, 'ABC', 'DEF', 12, 1993-09-12)");
//            logger.info("Insert statement result : {}",result >= 1 ? true : false);
//           (OR)
//
//            //Dynamic Query, making Query compile only once but can execute any number of times
//            PreparedStatement statement = connection.prepareStatement(
//                    "Insert into Person(firstName, lastName, age, dob) values (?,?,?,?)"
//            );
//            statement.setString(1, "ABC");
//            statement.setString(2, "DEF");
//            statement.setInt(3,24);
//            statement.setString(4, "1882-02-17");
//            statement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}

/*
HIBERNATE
 */
//SQL QUERIES ARE NOT OPTIMISED
//HIBERNATE EXECUTES QUERIES IN MOST OPTIMISED MANNER
//MANAGING OF CONNECTIONS, CONNECTION POOL TO DATABASE IS ALSO HANDLED BY HIBERNATE - HOW MANY CONNECTIONS, HOW MANY THREADS
//CONVERSION OF JAVA OBJECT TO DB TABLE ROW IS AUTOMATICALLY HANDLED BY HIBERNATE

/*
*JPA
* - JAVA PERSISTENCE API
* A CONTRACT OR INTERFACE WITH FUNCTIONS TO SAVE, INSERT, DELETE ON A TABLE
* HIBERNATE IS THE DEFAULT IMPLEMENTATION OF JPA INTERFACE
* OTHER IMPLEMENTATIONS : ECLIPSE LINK, OPEN JPA
* JDBC IS INDEPENDENT OF JPA
* JPA IS MORE POWERFUL THAN JPA - JDBC IS A PARENT DEPENDENCY OF JPA
*
* JPA IS ANALOGUES TO SPRING BOOT STARTER WEB
* HIBERNATE IS ANALOGUES TO TOMCAT SERVER
 */