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
        assertEquals(4, YatzyJava.Category.TWOS.score(new YatzyJava.DiceRoll(2, 2, 3, 4, 5)));
        assertEquals(9, YatzyJava.Category.THREES.score(new YatzyJava.DiceRoll(3, 3, 3, 2, 1)));
        assertEquals(8, YatzyJava.Category.FOURS.score(new YatzyJava.DiceRoll(4, 4, 1, 2, 3)));
        assertEquals(10, YatzyJava.Category.FIVES.score(new YatzyJava.DiceRoll(5, 1, 5, 2, 3)));
        assertEquals(0, YatzyJava.Category.SIXES.score(new YatzyJava.DiceRoll(1, 2, 3, 4, 5)));
    }

    @Test
    void testYatzy() {
        assertEquals(50, YatzyJava.Category.YATZY.score(new YatzyJava.DiceRoll(4, 4, 4, 4, 4)));
        assertEquals(0, YatzyJava.Category.YATZY.score(new YatzyJava.DiceRoll(4, 4, 4, 4, 5)));
    }

    @Test
    void testPairsAndKinds() {
        assertEquals(12, YatzyJava.Category.PAIR.score(new YatzyJava.DiceRoll(6, 6, 1, 2, 3)));
        assertEquals(10, YatzyJava.Category.PAIR.score(new YatzyJava.DiceRoll(6, 5, 5, 5, 5)));
        assertEquals(9, YatzyJava.Category.THREE_OF_A_KIND.score(new YatzyJava.DiceRoll(3, 3, 3, 1, 2)));
        assertEquals(0, YatzyJava.Category.FOUR_OF_A_KIND.score(new YatzyJava.DiceRoll(3, 3, 3, 1, 2)));
        assertEquals(16, YatzyJava.Category.FOUR_OF_A_KIND.score(new YatzyJava.DiceRoll(4, 4, 4, 4, 2)));
    }

    @Test
    void testChance() {
        assertEquals(21, YatzyJava.Category.CHANCE.score(new YatzyJava.DiceRoll(6, 6, 1, 2, 6)));
    }

    @Test
    void testTwoPairs() {
        assertEquals(16, YatzyJava.Category.TWO_PAIRS.score(new YatzyJava.DiceRoll(3, 3, 5, 5, 1)));
        assertEquals(0, YatzyJava.Category.TWO_PAIRS.score(new YatzyJava.DiceRoll(1, 2, 2, 3, 4)));
        assertEquals(10, YatzyJava.Category.TWO_PAIRS.score(new YatzyJava.DiceRoll(2, 2, 3, 3, 3)));
    }

    @Test
    void testStraights() {
        assertEquals(15, YatzyJava.Category.SMALL_STRAIGHT.score(new YatzyJava.DiceRoll(1, 2, 3, 4, 5)));
        assertEquals(20, YatzyJava.Category.LARGE_STRAIGHT.score(new YatzyJava.DiceRoll(2, 3, 4, 5, 6)));
    }

}
