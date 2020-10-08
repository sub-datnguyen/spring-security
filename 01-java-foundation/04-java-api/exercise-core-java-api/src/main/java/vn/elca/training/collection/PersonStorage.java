package vn.elca.training.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import vn.elca.training.helper.Person;

/**
 * @author hay
 */
public class PersonStorage {

    private Map<String, Person> persons = new HashMap<>();

    public Person getOrCreate(String name, String description) {
        if (persons.containsKey(name)) {
            return persons.get(name);
        } else {
            System.out.println("Person with name " + name + " created");
            Person person = new Person(name);
            person.setDescription(description);
            persons.put(name, person);
            return person;
        }
    }

}
