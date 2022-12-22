package ru.mochegov.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.mochegov.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_CONT = 0;
    private List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_CONT, "Dmitry", 41, "mochegov@gmail.com" ));
        people.add(new Person(++PEOPLE_CONT, "Svetlana", 37, "mochegova@gmail.com"));
        people.add(new Person(++PEOPLE_CONT, "Harry Potter", 17, "potter@gmail.com"));
        people.add(new Person(++PEOPLE_CONT, "James Bond", 50, "jamesbond@gmail.com"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        Person resultPerson = null;

        for (Person person: people) {
            if (person.getId() == id) {
                resultPerson = person;
            }
        }
        return resultPerson;
    }

    public void save(Person person) {
        person.setId(++PEOPLE_CONT);
        people.add(person);
    }

    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = show(id);
        if (personToBeUpdated != null) {
            personToBeUpdated.setName(updatedPerson.getName());
            personToBeUpdated.setAge(updatedPerson.getAge());
            personToBeUpdated.setEmail(updatedPerson.getEmail());
        }
    }

    public void delete(int id) {
        int indexToDelete;
        Person personToBeDeleted = show(id);
        if (personToBeDeleted != null) {
            indexToDelete = people.indexOf(personToBeDeleted);
            people.remove(indexToDelete);
        }
    }
}
