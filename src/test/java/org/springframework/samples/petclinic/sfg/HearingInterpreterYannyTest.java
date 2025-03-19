package org.springframework.samples.petclinic.sfg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig({BaseConfig.class, YannyConfig.class})
class HearingInterpreterYannyTest {
    @Autowired
    private HearingInterpreter interpreter;

    @Test
    void whatIHeard() {
        String word = interpreter.whatIHeard();

        assertEquals("Yanny", word);
    }
}