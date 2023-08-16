package com.example.guessnumberappdemo;

public class HintProvider {
    public String provideHint (int number) {
        if (number % 2 == 0) {
            return "It's a even number";
        }
        else {
            return "It's a odd number";
        }
    }
}
