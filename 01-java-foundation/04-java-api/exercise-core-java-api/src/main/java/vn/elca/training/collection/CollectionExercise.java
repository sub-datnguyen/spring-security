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
