package com.edutech.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class InscricaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inscricao getInscricaoSample1() {
        return new Inscricao().id(1L);
    }

    public static Inscricao getInscricaoSample2() {
        return new Inscricao().id(2L);
    }

    public static Inscricao getInscricaoRandomSampleGenerator() {
        return new Inscricao().id(longCount.incrementAndGet());
    }
}
