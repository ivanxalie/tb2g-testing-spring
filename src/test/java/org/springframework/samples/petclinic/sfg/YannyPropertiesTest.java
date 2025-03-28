package org.springframework.samples.petclinic.sfg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.impl.HearingInterpreter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("externalized")
@SpringJUnitConfig(YannyPropertiesTest.TestConfig.class)
@TestPropertySource("classpath:yanny.properties")
public class YannyPropertiesTest {
    @Autowired
    private HearingInterpreter interpreter;

    @Test
    void whatIHeard() {
        assertEquals("YaNNy", interpreter.whatIHeard());
    }

    @Configuration
    @ComponentScan(value = "org.springframework.samples.petclinic.sfg.impl")
    static class TestConfig {

    }
}
