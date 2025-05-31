package model;

import java.util.*;
import java.util.stream.Collectors;

public class DiceRoll {

        private final List<Integer> dice;
        private final Map<Integer, Long> counts;

        public DiceRoll(int... values) {
            this.dice = Arrays.stream(values).boxed().collect(Collectors.toList());
            this.counts = dice.stream().collect(Collectors.groupingBy(d -> d, Collectors.counting()));
        }

        public int sum() {
            return dice.stream().mapToInt(i -> i).sum();
        }

        public int sumOf(int number) {
            return number * counts.getOrDefault(number, 0L).intValue();
        }

        public boolean allSame() {
            return counts.size() == 1;
        }

        public int nOfAKind(int n) {
            return counts.entrySet().stream()
                    .filter(e -> e.getValue() >= n)
                    .mapToInt(e -> e.getKey() * n)
                    .max()
                    .orElse(0);
        }

        public int highestNOfAKind(int n) {
            return counts.keySet().stream()
                    .filter(k -> counts.get(k) >= n)
                    .max(Integer::compareTo)
                    .map(k -> k * n)
                    .orElse(0);
        }

        public int twoPairs() {
            List<Integer> pairs = counts.entrySet().stream()
                    .filter(e -> e.getValue() >= 2)
                    .map(Map.Entry::getKey)
                    .sorted(Comparator.reverseOrder())
                    .limit(2)
                    .toList();
            return pairs.size() == 2 ? pairs.get(0) * 2 + pairs.get(1) * 2 : 0;
        }

        public int fullHouse() {
            Optional<Integer> three = counts.entrySet().stream()
                    .filter(e -> e.getValue() == 3)
                    .map(Map.Entry::getKey)
                    .findFirst();
            Optional<Integer> two = counts.entrySet().stream()
                    .filter(e -> e.getValue() == 2)
                    .map(Map.Entry::getKey)
                    .findFirst();
            return (three.isPresent() && two.isPresent()) ? three.get() * 3 + two.get() * 2 : 0;
        }

        public boolean matchesExact(int... expected) {
            List<Integer> sorted = new ArrayList<>(dice);
            Collections.sort(sorted);
            return Arrays.equals(sorted.stream().mapToInt(Integer::intValue).toArray(), expected);
        }


        @Override
        public String toString() {
            return dice.toString();
        }

    }

