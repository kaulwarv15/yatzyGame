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
    }


}
