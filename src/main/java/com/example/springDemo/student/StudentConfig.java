package com.example.springDemo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student first = new Student(
                    "first",
                    "first@gmail.com",
                    LocalDate.of(1985, Month.OCTOBER, 8));

            Student second = new Student(
                    "second",
                    "second@gmail.com",
                    LocalDate.of(1995, Month.AUGUST, 5));

            repository.saveAll(List.of(first, second));
        };
    }
}
