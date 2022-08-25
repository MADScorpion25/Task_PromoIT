package com.company;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class FrequencyDictionary {
    private final String string;
    private Map<Character, Long> frequencies;
    private float averageFrequency;

    public FrequencyDictionary(String string) {
        if(string != null) this.string = string;
        else this.string = "";
        frequencies = new HashMap<>();
    }

    public Map<Character, Long> computeFrequencies() {
        char[] symbols = string.toCharArray();
        frequencies = IntStream.range(0, symbols.length).mapToObj(i -> symbols[i])
                .filter(symbol -> Character.isLetter(symbol) || Character.isDigit(symbol))
                .collect(groupingBy(symbol -> symbol, counting()));
        return frequencies;
    }

    public void computeAverageFrequency() {
        averageFrequency = (float) getLettersAndDigitsCount() / frequencies.size();
    }

    public List<Character> getAverageFrequentSymbols() {
        Set<Map.Entry<Character, Long>> entries = frequencies.entrySet();

        final Long closestFrequency = entries.stream()
                .min(Comparator.comparing(frequency -> Math.abs(averageFrequency - frequency.getValue())))
                .map(Map.Entry::getValue).orElse(0L);

        return entries.stream()
                .filter(entry -> entry.getValue().equals(closestFrequency))
                .map(Map.Entry::getKey)
                .collect(toList());
    }

    public String getString() {
        return string;
    }

    public Map<Character, Long> getFrequencies() {
        return frequencies;
    }

    public int getLettersAndDigitsCount() {
        return string.replaceAll("[^A-Za-zА-Яа-я0-9ё]", "").length();
    }

    public float getAverageFrequency() {
        return averageFrequency;
    }
}
