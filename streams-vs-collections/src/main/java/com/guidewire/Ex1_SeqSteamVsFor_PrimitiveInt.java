package com.guidewire;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class Ex1_SeqSteamVsFor_PrimitiveInt {

    private int[] randomInts;

    @Param({"50000"})
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

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(Ex1_SeqSteamVsFor_PrimitiveInt.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
