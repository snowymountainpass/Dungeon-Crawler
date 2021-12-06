package com.codecool.dungeoncrawl.logic.utils;

import java.util.Random;

public class Randomizers {

    private static final Random RANDOM = new Random();


    public static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static int getRandomIntInRange ( int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }




}
