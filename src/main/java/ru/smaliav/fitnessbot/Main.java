package ru.smaliav.fitnessbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@Slf4j
@SpringBootApplication
public class Main {

    private static final Map<String, String> env = System.getenv();

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
