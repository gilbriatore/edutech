package com.edutech.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AulaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aula getAulaSample1() {
        return new Aula().id(1L).titulo("titulo1");
    }

    public static Aula getAulaSample2() {
        return new Aula().id(2L).titulo("titulo2");
    }

    public static Aula getAulaRandomSampleGenerator() {
        return new Aula().id(longCount.incrementAndGet()).titulo(UUID.randomUUID().toString());
    }
}
