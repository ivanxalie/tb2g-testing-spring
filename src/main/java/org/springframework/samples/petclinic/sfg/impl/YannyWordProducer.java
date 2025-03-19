package org.springframework.samples.petclinic.sfg.impl;

import org.springframework.stereotype.Component;

@Component
public class YannyWordProducer implements WordProducer {
    @Override
    public String word() {
        return "Yanny";
    }
}
