package vn.elca.training.string;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @author hay
 */
public class StringExercise {
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //Improve this code performance to handle large number of input e.g 1000000
        repeat("StringToRepeat", 100000);
        stopWatch.stop();
        System.out.println("Repeat string execution time: " + stopWatch.getTime() + "ms");
    }

    public static String repeat(String input, int repeat) {
        String result = "";
        for (int i = 0; i < repeat; i++) {
            result += input;
        }
        return result;
    }

}
