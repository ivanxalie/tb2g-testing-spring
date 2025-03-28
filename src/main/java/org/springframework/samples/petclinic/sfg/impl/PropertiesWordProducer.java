package org.springframework.samples.petclinic.sfg.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("externalized")
public class PropertiesWordProducer implements WordProducer {
    private String word;

    @Value("${say.word}")
    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String word() {
        return word;
    }
}
