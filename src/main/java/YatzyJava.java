import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class YatzyJava {


    public enum Category {
        ONES(dice -> dice.sumOf(1)),
        TWOS(dice -> dice.sumOf(2)),
        THREES(dice -> dice.sumOf(3)),
        FOURS(dice -> dice.sumOf(4)),
        FIVES(dice -> dice.sumOf(5)),
        SIXES(dice -> dice.sumOf(6)),
        PAIR(dice -> dice.highestNOfAKind(2)),
        TWO_PAIRS(DiceRoll::twoPairs),
        THREE_OF_A_KIND(dice -> dice.nOfAKind(3)),
        FOUR_OF_A_KIND(dice -> dice.nOfAKind(4)),
        CHANCE(DiceRoll::sum),
        YATZY(dice -> dice.allSame() ? 50 : 0);

        private final Function<DiceRoll, Integer> scorer;

        Category(Function<DiceRoll, Integer> scorer) {
            this.scorer = scorer;
        }

        public int score(DiceRoll dice) {
            return scorer.apply(dice);
        }
    }

    public static class DiceRoll {
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

}


