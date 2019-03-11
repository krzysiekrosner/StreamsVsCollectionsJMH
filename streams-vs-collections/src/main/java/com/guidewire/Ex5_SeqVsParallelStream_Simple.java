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
public class Ex5_SeqVsParallelStream_Simple {

    private ArrayList<Integer> randomInts;

    @Param({"100", "1000", "10000", "100000", "500000"})
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
    public void sequentialStreamMaxSearchCPU(Blackhole bh) {
        int maxStream = randomInts.stream()
                .max(Integer::compareTo)
                .get();

        bh.consume(maxStream);
    }

    @Benchmark
    public void parallelStreamMaxSearchCPU(Blackhole bh) {
        int maxStream = randomInts.parallelStream()
                .max(Integer::compareTo)
                .get();

        bh.consume(maxStream);
    }
}
