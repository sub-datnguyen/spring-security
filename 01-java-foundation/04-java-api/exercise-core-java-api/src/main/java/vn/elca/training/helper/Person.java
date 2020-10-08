package vn.elca.training.helper;

import java.util.Objects;

/**
 * @author hay
 */
public class Person {

    private String name;
    private String description;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        Person a = (Person) o;
        return Objects.equals(name, a.name);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return 42;
    }

    @Override
    public String toString() {
        return description;
    }
}
