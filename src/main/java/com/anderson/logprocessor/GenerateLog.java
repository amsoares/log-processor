package com.anderson.logprocessor;

import com.anderson.logprocessor.generator.LogGenerator;

public class GenerateLog {

    public static void main(String[] args) {
        new LogGenerator(10, 10).generate("./src/main/resources/generated/logs/");
    }
}
