package com.company;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) {
        ApiConnection apiConnection = new ApiConnection();
        String phrase;
        try {
            phrase = apiConnection.getRequestString();
        } catch (IOException e) {
            phrase = "";
        }

        System.out.println(apiConnection.getUrl());
        System.out.println(phrase);

        FrequencyDictionary frequencyDictionary = new FrequencyDictionary(phrase);

        System.out.println("Частоты:");
        frequencyDictionary.computeFrequencies()
                .forEach((key, value) -> System.out.println(key + " - " + value));

        frequencyDictionary.computeAverageFrequency();

        System.out.println("Среднее значение частоты: " + frequencyDictionary.getLettersAndDigitsCount()
                + " / " + frequencyDictionary.getFrequencies().size()
                + " = "
                + frequencyDictionary.getAverageFrequency());

        System.out.println("Символы, наиболее близкие к среднему значению частоты:");
        frequencyDictionary.getAverageFrequentSymbols()
                .forEach(symbol -> System.out.print(symbol + "(" + (int)symbol + ") "));
    }
}