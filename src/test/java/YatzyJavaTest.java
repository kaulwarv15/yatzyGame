import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class YatzyJavaTest {

    @Test
    void testDiceRollSumAndMatches() {
        YatzyJava.DiceRoll roll = new YatzyJava.DiceRoll(1, 2, 3, 4, 5);
        assertEquals(15, roll.sum());
        assertTrue(roll.matchesExact(1, 2, 3, 4, 5));
    }

    @Test
    void testOnesToSixes() {
        assertEquals(2, YatzyJava.Category.ONES.score(new YatzyJava.DiceRoll(1, 1, 3, 4, 5)));
        assertEquals(9, YatzyJava.Category.THREES.score(new YatzyJava.DiceRoll(3, 3, 3, 2, 1)));
        assertEquals(0, YatzyJava.Category.SIXES.score(new YatzyJava.DiceRoll(1, 2, 3, 4, 5)));
    }

    @Test
    void testYatzy() {
        assertEquals(50, YatzyJava.Category.YATZY.score(new YatzyJava.DiceRoll(4, 4, 4, 4, 4)));
        assertEquals(0, YatzyJava.Category.YATZY.score(new YatzyJava.DiceRoll(4, 4, 4, 4, 5)));
    }
}
