package org.springframework.samples.petclinic.sfg.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("laurel")
public class LaurelWordProducer implements WordProducer {
    @Override
    public String word() {
        return "Laurel";
    }
}
