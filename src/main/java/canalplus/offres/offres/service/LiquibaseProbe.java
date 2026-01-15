package canalplus.offres.offres.service;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class LiquibaseProbe {

    @PostConstruct
    public void probe() {
        System.out.println(">>> LIQUIBASE PROBE LOADED <<<");
    }
}
