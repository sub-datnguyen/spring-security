/*
 * Project: EGDD
 *
 * Copyright 2020 by Canton de Vaud
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of Canton de Vaud. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreement you entered into with Canton de Vaud.
 */
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

    private Map<String, Person> persons = new ConcurrentHashMap<>();
    private AtomicInteger createCount = new AtomicInteger(0);

    public Person getOrCreate(String name, String description) {
        if (persons.containsKey(name)) {
            return persons.get(name);
        } else {
            createCount.addAndGet(1);
            Person person = new Person(name);
            person.setDescription(description);
            persons.put(name, person);
            return person;
        }
    }

    public AtomicInteger getCreateCount() {
        return createCount;
    }
}
