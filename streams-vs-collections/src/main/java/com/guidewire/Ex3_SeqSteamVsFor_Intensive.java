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
public class Ex3_SeqSteamVsFor_Intensive {

    private ArrayList<Integer> randomInts;

    @Param({"1000", "5000", "10000"})
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
    public void forLoopMaxSearchCPU(Blackhole bh) {
        double maxFor = Double.MIN_VALUE;
        for (int anInt : randomInts) {
            double aDouble = cpuIntensiveOperation(anInt);
            if (aDouble > maxFor) maxFor = aDouble;
        }
        bh.consume(maxFor);
    }

    @Benchmark
    public void sequentialStreamMaxSearchCPU(Blackhole bh) {
        double maxStream = randomInts.stream()
                .map(this::cpuIntensiveOperation)
                .max(Double::compareTo)
                .get();

        bh.consume(maxStream);
    }

    private double cpuIntensiveOperation(int anInt) {
        return Math.sqrt((Math.tan(anInt) + Math.sin(anInt) / 97.0) * (Math.sqrt(37.3) + 2));
    }
}
