package com.pf.app;

import org.springframework.stereotype.Component;

import java.util.Scanner;

// this class can be used to do all the read operations related to the file/s and console
@Component
public class ReadInput {

    public String readInputFromConsole(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
