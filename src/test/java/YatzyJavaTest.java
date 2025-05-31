
//package test.java;

import model.DiceRoll;
import model.Category;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class YatzyJavaTest {

    @Test
    void testDiceRollSumAndMatches() {
        DiceRoll roll = new DiceRoll(1, 2, 3, 4, 5);
        assertEquals(15, roll.sum());
        assertTrue(roll.matchesExact(1, 2, 3, 4, 5));
    }

    @Test
    void testOnesToSixes() {
        assertEquals(2, Category.ONES.score(new DiceRoll(1, 1, 3, 4, 5)));
        assertEquals(4, Category.TWOS.score(new DiceRoll(2, 2, 3, 4, 5)));
        assertEquals(9, Category.THREES.score(new DiceRoll(3, 3, 3, 2, 1)));
        assertEquals(8, Category.FOURS.score(new DiceRoll(4, 4, 1, 2, 3)));
        assertEquals(10, Category.FIVES.score(new DiceRoll(5, 1, 5, 2, 3)));
        assertEquals(0, Category.SIXES.score(new DiceRoll(1, 2, 3, 4, 5)));
    }

    @Test
    void testYatzy() {
        assertEquals(50, Category.YATZY.score(new DiceRoll(4, 4, 4, 4, 4)));
        assertEquals(0, Category.YATZY.score(new DiceRoll(4, 4, 4, 4, 5)));
    }

    @Test
    void testPairsAndKinds() {
        assertEquals(12, Category.PAIR.score(new DiceRoll(6, 6, 1, 2, 3)));
        assertEquals(10, Category.PAIR.score(new DiceRoll(6, 5, 5, 5, 5)));
        assertEquals(9, Category.THREE_OF_A_KIND.score(new DiceRoll(3, 3, 3, 1, 2)));
        assertEquals(0, Category.FOUR_OF_A_KIND.score(new DiceRoll(3, 3, 3, 1, 2)));
        assertEquals(16, Category.FOUR_OF_A_KIND.score(new DiceRoll(4, 4, 4, 4, 2)));
    }

    @Test
    void testChance() {
        assertEquals(21, Category.CHANCE.score(new DiceRoll(6, 6, 1, 2, 6)));
    }

    @Test
    void testTwoPairs() {
        assertEquals(16, Category.TWO_PAIRS.score(new DiceRoll(3, 3, 5, 5, 1)));
        assertEquals(0, Category.TWO_PAIRS.score(new DiceRoll(1, 2, 2, 3, 4)));
        assertEquals(10, Category.TWO_PAIRS.score(new DiceRoll(2, 2, 3, 3, 3)));
    }

    @Test
    void testStraights() {
        assertEquals(15, Category.SMALL_STRAIGHT.score(new DiceRoll(1, 2, 3, 4, 5)));
        assertEquals(20, Category.LARGE_STRAIGHT.score(new DiceRoll(2, 3, 4, 5, 6)));
    }

    @Test
    void testFullHouse() {
        assertEquals(24, Category.FULL_HOUSE.score(new DiceRoll(6, 6, 6, 3, 3)));
        assertEquals(0, Category.FULL_HOUSE.score(new DiceRoll(2, 2, 3, 3, 4)));
    }

}
