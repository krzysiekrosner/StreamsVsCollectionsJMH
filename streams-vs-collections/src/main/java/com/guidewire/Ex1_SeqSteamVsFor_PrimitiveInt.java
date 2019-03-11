package com.guidewire;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, warmups = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class Ex1_SeqSteamVsFor_PrimitiveInt {

    private int[] randomInts;

    @Param({"1000", "10000", "50000", "200000"})
    private int arraySize;

    @Setup
    public void setUp() {
        randomInts = new int[arraySize];
        Random ran = new Random();
        for (int i = 0; i < randomInts.length; i++) {
            randomInts[i] = ran.nextInt();
        }
    }

    @Benchmark
    public void forLoopMaxSearchInt(Blackhole bh) {
        int maxFor = Integer.MIN_VALUE;
        for (int j = 0; j < arraySize; j++) {
            if (randomInts[j] > maxFor) maxFor = randomInts[j];
        }
        bh.consume(maxFor);
    }

    @Benchmark
    public void sequentialStreamMaxSearchInt(Blackhole bh) {
        int maxStream = Arrays.stream(randomInts).max().getAsInt();
        bh.consume(maxStream);
    }
}
