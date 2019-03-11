package com.guidewire;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, warmups = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class Ex6_ParallelStream_LinkedAndArrayList {

    private LinkedList<Integer> randomInts1;
    private ArrayList<Integer> randomInts2;

    @Param({"10000", "50000", "100000"})
    private int arraySize;

    @Setup
    public void setUp() {
        Random ran = new Random();

        randomInts1 = new LinkedList<>();
        randomInts2 = new ArrayList<>();
        for (int i = 0; i < arraySize; i++) {
            randomInts1.add(ran.nextInt());
            randomInts2.add(ran.nextInt());
        }
    }

    @Benchmark
    public void parallelStreamMaxLinkedList(Blackhole bh) {
        int maxStream = randomInts1.stream()
                .max(Integer::compareTo)
                .get();

        bh.consume(maxStream);
    }

    @Benchmark
    public void parallelStreamMaxArrayList(Blackhole bh) {
        int maxStream = randomInts2.parallelStream()
                .max(Integer::compareTo)
                .get();

        bh.consume(maxStream);
    }

}
