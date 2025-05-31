import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class YatzyJava {


    public enum Category {
        ONES(dice -> dice.sumOf(1)),
        THREES(dice -> dice.sumOf(3)),
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


