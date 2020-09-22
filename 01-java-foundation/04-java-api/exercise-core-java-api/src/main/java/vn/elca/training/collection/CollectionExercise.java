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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import vn.elca.training.helper.Person;

/**
 * @author hay
 */
public class CollectionExercise {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        PersonStorage personStorage = new PersonStorage();
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> personStorage.getOrCreate("Nguyen", "Employee"));
        }
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        executorService.shutdown();
        // Eliminate the duplicated person creation
        System.out.println("Duplicated person creation count: " + personStorage.getCreateCount());
    }

}
