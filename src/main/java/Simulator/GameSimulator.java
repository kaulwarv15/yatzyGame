package Simulator;

import Output.FileOutput;
import Output.OutputStrategy;
import model.Category;
import model.DiceRoll;

import java.util.*;

public class GameSimulator {
    private final Random random;
    private final OutputStrategy output;

    public GameSimulator(OutputStrategy output) {
        this(output, new Random());
    }

    public GameSimulator(OutputStrategy output, Random random) {
        this.output = output;
        this.random = random;
    }

    public void simulateGame(int rounds) {
        for (int i = 1; i <= rounds; i++) {
            int[] roll = random.ints(5, 0, 6).toArray();
            DiceRoll diceRoll = new DiceRoll(roll);
            Category chosenCategory = chooseCategory(diceRoll);
            int score = chosenCategory.score(diceRoll);
            String outcome = getOutcome(chosenCategory, score);
            output.print(i + ". ROLL");
            output.print("You've chosen " + chosenCategory + " as score category");
            output.print("Score: " + score);
            output.print("You've got " + outcome + "\n");
        }
        if (output instanceof FileOutput f) {
            f.close();
        }
    }

    public Category chooseCategory(DiceRoll roll) {
        return Arrays.stream(Category.values())
                .max(Comparator.comparingInt(c -> c.score(roll)))
                .orElse(Category.CHANCE);
    }

    public String getOutcome(Category category, int score) {
        if (score == 0) return "NOTHING";
        return switch (category) {
            case YATZY -> "a YATZY";
            case FULL_HOUSE -> "a FULL_HOUSE";
            case SMALL_STRAIGHT -> "a SMALL_STRAIGHT";
            case LARGE_STRAIGHT -> "a LARGE_STRAIGHT";
            case FOUR_OF_A_KIND -> "FOUR_OF_A_KIND";
            case THREE_OF_A_KIND -> "THREE_OF_A_KIND";
            case TWO_PAIRS -> "TWO_PAIRS";
            case PAIR -> "a PAIR";
            case ONES, TWOS, THREES, FOURS, FIVES, SIXES -> "some " + category.name();
            case CHANCE -> "a total score of " + score;
        };
    }
}

