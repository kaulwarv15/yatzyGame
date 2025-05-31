
import Output.FileOutput;
import Output.OutputStrategy;
import Simulator.GameSimulator;
import model.DiceRoll;
import model.Category;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void chooseCategory_shouldReturnCorrectCategory() {
        DiceRoll roll = new DiceRoll(6, 6, 6, 3, 3);
        Category best = Category.FULL_HOUSE;
        int bestScore = best.score(roll);
        for (Category category : Category.values()) {
            assertTrue(bestScore >= category.score(roll),
                    () -> "Expected FULL_HOUSE to be equal or better than " + category);
        }
    }

    @Test
    void testWriteAndCloseFile() throws IOException {
        String fileName = "temp_output.txt";
        FileOutput fileOutput = new FileOutput(fileName);

        fileOutput.print("Hello Yatzy");
        fileOutput.print("Testing FileOutput class");
        fileOutput.close();

        File file = new File(fileName);
        assertTrue(file.exists(), "File should exist after writing");

        List<String> lines = Files.readAllLines(file.toPath());
        assertEquals(2, lines.size(), "Should have written 2 lines");
        assertEquals("Hello Yatzy", lines.get(0));
        assertEquals("Testing FileOutput class", lines.get(1));

        assertTrue(file.delete(), "Temporary file should be deleted");
    }



    @Test
    void testGivenRollsSimulation() {
        List<String> capturedOutput = new ArrayList<>();
        OutputStrategy mockOutput = capturedOutput::add;

        GameSimulator simulator = new GameSimulator(mockOutput);

        List<int[]> rolls = List.of(
                new int[]{1, 1, 1, 1, 1},
                new int[]{3, 3, 3, 2, 4},
                new int[]{1, 2, 3, 4, 5}
        );

        List<Category> categories = List.of(
                Category.YATZY,
                Category.THREE_OF_A_KIND,
                Category.SMALL_STRAIGHT
        );

        simulator.simulateGivenRolls(rolls, categories);

        assertThat(capturedOutput).hasSize(12);
        assertThat(capturedOutput.get(1)).contains("YATZY");
        assertThat(capturedOutput.get(5)).contains("THREE_OF_A_KIND");
        assertThat(capturedOutput.get(9)).contains("SMALL_STRAIGHT");
    }

    @Test
    void testSimulateGivenRolls_withMockOutput() {
        List<String> output = new ArrayList<>();
        OutputStrategy mockOutput = output::add;

        List<int[]> rolls = List.of(
                new int[]{1, 1, 1, 1, 1},
                new int[]{2, 2, 2, 3, 4},
                new int[]{1, 2, 3, 4, 5}
        );
        List<Category> categories = List.of(
                Category.YATZY,
                Category.THREE_OF_A_KIND,
                Category.SMALL_STRAIGHT
        );

        GameSimulator simulator = new GameSimulator(mockOutput);
        simulator.simulateGivenRolls(rolls, categories);

        assertThat(output).hasSize(12);
        assertThat(output.get(0)).contains("1. ROLL");
        assertThat(output.get(1)).contains("You've chosen YATZY");
        assertThat(output.get(3)).contains("You've got a YATZY");

        assertThat(output.get(4)).contains("2. ROLL");
        assertThat(output.get(5)).contains("You've chosen THREE_OF_A_KIND");
        assertThat(output.get(7)).contains("You've got a THREE_OF_A_KIND");

        assertThat(output.get(8)).contains("3. ROLL");
        assertThat(output.get(9)).contains("You've chosen SMALL_STRAIGHT");
        assertThat(output.get(11)).contains("You've got a SMALL_STRAIGHT");
    }

    @Test
    void testGetOutcomeStringsSimple() {
        GameSimulator simulator = new GameSimulator(output -> {});
        assertEquals("NOTHING", simulator.getOutcome(Category.YATZY, 0));
        assertEquals("a YATZY", simulator.getOutcome(Category.YATZY, 50));
        assertEquals("a FULL_HOUSE", simulator.getOutcome(Category.FULL_HOUSE, 18));
        assertEquals("TWO_PAIRS", simulator.getOutcome(Category.TWO_PAIRS, 8));
        assertEquals("some FOURS", simulator.getOutcome(Category.FOURS, 8));
        assertEquals("a total score of 17", simulator.getOutcome(Category.CHANCE, 17));
    }
}

