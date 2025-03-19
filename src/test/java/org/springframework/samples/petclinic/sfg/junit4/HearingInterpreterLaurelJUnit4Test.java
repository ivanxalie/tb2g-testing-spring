package org.springframework.samples.petclinic.sfg.junit4;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.sfg.config.BaseConfig;
import org.springframework.samples.petclinic.sfg.impl.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.config.LaurelConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, LaurelConfig.class})
public class HearingInterpreterLaurelJUnit4Test {
    @Autowired
    HearingInterpreter interpreter;

    @Test
    public void whatIHeard() {
        String word = interpreter.whatIHeard();

        assertEquals("Laurel", word);
    }
}