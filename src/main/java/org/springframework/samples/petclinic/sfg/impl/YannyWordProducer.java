package org.springframework.samples.petclinic.sfg.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("yanny")
public class YannyWordProducer implements WordProducer {
    @Override
    public String word() {
        return "Yanny";
    }
}
