package vn.elca.training.string;/*
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
