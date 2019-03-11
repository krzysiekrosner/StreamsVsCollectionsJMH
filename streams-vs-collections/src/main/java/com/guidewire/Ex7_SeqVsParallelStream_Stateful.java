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
public class Ex7_SeqVsParallelStream_Stateful {

    private ArrayList<Integer> randomInts;

    @Param({"100", "10000", "100000"})
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
    public void sequentialStreamDistinctCount(Blackhole bh) {
        long count = randomInts.stream()
                .map(anInt -> anInt - 2)
                .distinct()
                .map(anInt -> anInt + 2)
                .count();

        bh.consume(count);
    }

    @Benchmark
    public void parallelStreamDistinctCount(Blackhole bh) {
        long count = randomInts.parallelStream()
                .map(anInt -> anInt - 2)
                .distinct()
                .map(anInt -> anInt + 2)
                .count();

        bh.consume(count);
    }

}
