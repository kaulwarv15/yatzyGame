
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
    void testFileOutputStrategy() throws IOException {
        String fileName = "test_output.txt";
        OutputStrategy fileOut = new FileOutput(fileName);
        new GameSimulator(fileOut, new Random(42)).simulateGame(1);

        List<String> lines = Files.readAllLines(new File(fileName).toPath());
        assertTrue(lines.stream().anyMatch(s -> s.contains("ROLL")));
        assertTrue(lines.stream().anyMatch(s -> s.contains("Score")));
        assertTrue(lines.stream().anyMatch(s -> s.contains("chosen")));
        new File(fileName).delete();
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
    void testGameSimulationOutput() {
        List<String> capturedOutput = new ArrayList<>();
        OutputStrategy mockOutput = capturedOutput::add;

        GameSimulator simulator = new GameSimulator(mockOutput, new Random(1));
        simulator.simulateGame(1); // deterministic roll for testing

        assertThat(capturedOutput).hasSize(4);
        assertThat(capturedOutput.get(0)).contains("1. ROLL");
        assertThat(capturedOutput.get(1)).contains("You've chosen");
        assertThat(capturedOutput.get(2)).contains("Score:");
        assertThat(capturedOutput.get(3)).contains("You've got");
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
    void testGetOutcomeTextCases() {
        GameSimulator simulator = new GameSimulator(System.out::println);

        assertEquals("a YATZY", simulator.getOutcome(Category.YATZY, 50));
        assertEquals("a FULL_HOUSE", simulator.getOutcome(Category.FULL_HOUSE, 24));
        assertEquals("a SMALL_STRAIGHT", simulator.getOutcome(Category.SMALL_STRAIGHT, 15));
        assertEquals("a LARGE_STRAIGHT", simulator.getOutcome(Category.LARGE_STRAIGHT, 20));
        assertEquals("FOUR_OF_A_KIND", simulator.getOutcome(Category.FOUR_OF_A_KIND, 16));
        assertEquals("THREE_OF_A_KIND", simulator.getOutcome(Category.THREE_OF_A_KIND, 9));
        assertEquals("TWO_PAIRS", simulator.getOutcome(Category.TWO_PAIRS, 10));
        assertEquals("a PAIR", simulator.getOutcome(Category.PAIR, 6));
        assertEquals("some FOURS", simulator.getOutcome(Category.FOURS, 4));
        assertEquals("a total score of 17", simulator.getOutcome(Category.CHANCE, 17));
        assertEquals("NOTHING", simulator.getOutcome(Category.FULL_HOUSE, 0));
    }

    @Test
    void testChooseCategoryReturnsMaxScore() {
        GameSimulator simulator = new GameSimulator(System.out::println);
        DiceRoll roll = new DiceRoll(6, 6, 6, 6, 6);
        Category bestCategory = simulator.chooseCategory(roll);
        assertEquals(Category.YATZY, bestCategory);
    }
}
