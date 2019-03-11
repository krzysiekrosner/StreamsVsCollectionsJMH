package com.guidewire;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, warmups = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class Ex2_SeqSteamVsFor_BoxedInt{

    private ArrayList<Integer> randomInts;

    @Param({"1000", "10000", "50000", "200000"})
    private int arraySize;

    @Setup
    public void setUp() {
        randomInts = new ArrayList<>();
        Random ran = new Random();
        for (int i = 0; i < arraySize; i++) {
            randomInts.add(ran.nextInt());
        }
    }

    @Benchmark
    public void forLoopMaxSearchInteger(Blackhole bh) {
        int maxFor = Integer.MIN_VALUE;
        for (int anInt : randomInts) {
            if (anInt > maxFor) maxFor = anInt;
        }
        bh.consume(maxFor);
    }

    @Benchmark
    public void sequentialStreamMaxSearchInteger(Blackhole bh) {
        Integer maxStream = randomInts.stream().max(Integer::compareTo).get();
        bh.consume(maxStream);
    }
}
