package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Service;

@Service
public class HearingInterpreter {
    private final WordProducer producer;

    public HearingInterpreter(WordProducer producer) {
        this.producer = producer;
    }

    public String whatIHeard() {
        String word = producer.word();
        System.out.println(word);
        return word;
    }
}
