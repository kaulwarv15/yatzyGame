import java.util.*;
import java.util.stream.Collectors;

public class YatzyJava {

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



