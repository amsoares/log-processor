package com.anderson.logprocessor;

import com.anderson.logprocessor.generator.LogGenerator;

public class GenerateLog {

    public static void main(String[] args) {
        new LogGenerator(1000, 1000).generate("./src/main/resources/generated/logs/");
    }
}
