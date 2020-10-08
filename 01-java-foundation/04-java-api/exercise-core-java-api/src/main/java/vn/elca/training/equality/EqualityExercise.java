package vn.elca.training.equality;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.time.StopWatch;
import vn.elca.training.helper.Person;

/**
 * @author hay
 */
public class EqualityExercise {

    public static void main(String[] args) {
        Set<Person> personSet = new HashSet<>();
        StopWatch st = new StopWatch();
        st.start();
        //Improve this code performance to handle large number of person e.g 1000000
        for (int i = 0; i < 1000000; i++) {
            personSet.add(new Person("Name" + i));
        }
        st.stop();
        System.out.println("Build person set execution time: " + st.getTime() + "ms");
    }
}
