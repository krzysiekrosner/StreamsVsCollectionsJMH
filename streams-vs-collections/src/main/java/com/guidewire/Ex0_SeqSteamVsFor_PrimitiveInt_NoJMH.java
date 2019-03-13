package com.guidewire;

import java.util.Arrays;
import java.util.Random;

public class Ex0_SeqSteamVsFor_PrimitiveInt_NoJMH {

    private static int[] createArrayOfRandomInts() {
        int[] randomInts = new int[50000];
        Random ran = new Random();
        for (int i = 0; i < randomInts.length; i++) {
            randomInts[i] = ran.nextInt();
        }
        return randomInts;
    }


    public static void main(String[] args) {
        int[] ints = createArrayOfRandomInts();

        // For loop max search

        long startFor = System.nanoTime();

        int maxFor = Integer.MIN_VALUE;
        for (int anInt : ints) {
            if (anInt > maxFor) maxFor = anInt;
        }

        long finishFor = System.nanoTime();

        // Stream max search

        long startStream = System.nanoTime();

        int maxStram = Arrays.stream(ints).reduce(Integer.MIN_VALUE, Math::max);

        long finishStream = System.nanoTime();

        // Results

        long forTime = finishFor - startFor;
        long streamTime = finishStream - startStream;
        System.out.println("For loop max = " + maxFor + " and time = " + forTime);
        System.out.println("Stream max = " + maxStram + " and time = " + streamTime);
        System.out.println("Stream / loop time ratio = " + (double)streamTime / (double)forTime);

    }

}
