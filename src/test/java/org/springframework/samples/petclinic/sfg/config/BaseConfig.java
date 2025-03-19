package org.springframework.samples.petclinic.sfg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.impl.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.impl.WordProducer;

@Configuration
public class BaseConfig {

    @Bean
    HearingInterpreter interpreter(WordProducer producer) {
        return new HearingInterpreter(producer);
    }
}
