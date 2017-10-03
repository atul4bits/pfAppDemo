package com.pf.app;

import org.springframework.stereotype.Component;

@Component
public class Validation {

    public int validateIsInteger(String line){
        int val = 0;
        try
        {
            val = Integer.parseInt(line.trim());

        }
        catch (NumberFormatException ex)
        {
            // s is not an integer
            // can add to exceptions list for input line texts which were not numbers/integers
            // can be written to file
        }

        return val;
    }
}
