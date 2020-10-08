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
        // Each person with name should be created only once in PersonStorage
        // This code is creating duplicated Person with name "Nguyen"
        // Improve this code so that the person is created only once. Subsequent access to PersonStorage should return the created Person
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> personStorage.getOrCreate("Nguyen", "Employee"));
        }
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

}
