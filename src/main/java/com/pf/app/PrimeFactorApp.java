package com.pf.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Scanner;


@SpringBootApplication
public class PrimeFactorApp implements CommandLineRunner {

    @Autowired
    private ExecutePFApp executePFApp;

	public static void main(String[] args) {
        SpringApplication.run(PrimeFactorApp.class, args);

    }
	//access command line arguments
	@Override
	public void run(String... args) throws Exception {
        executePFApp.startExecution();
	}
}
