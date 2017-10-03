package com.pf.app;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrimeFactorImpl {

    public List<Integer> getPrimeFactor(int num){

        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= num / i; i++) {
            while (num % i == 0) {
                factors.add(i);
                num /= i;
            }
        }
        if (num > 1) {
            factors.add(num);
        }
        return factors;
    }
}
