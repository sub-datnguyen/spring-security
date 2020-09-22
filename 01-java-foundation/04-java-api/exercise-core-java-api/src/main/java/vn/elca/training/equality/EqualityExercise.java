package vn.elca.training.equality;/*
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
