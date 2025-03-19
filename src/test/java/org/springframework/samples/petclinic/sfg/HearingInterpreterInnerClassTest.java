package org.springframework.samples.petclinic.sfg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = HearingInterpreterInnerClassTest.TestConfig.class)
class HearingInterpreterInnerClassTest {
    @Autowired
    private HearingInterpreter interpreter;

    @Test
    void whatIHeard() {
        assertEquals("Laurel", interpreter.whatIHeard());
    }

    @Configuration
    static class TestConfig {

        @Bean
        HearingInterpreter interpreter() {
            return new HearingInterpreter(new LaurelWordProducer());
        }
    }
}