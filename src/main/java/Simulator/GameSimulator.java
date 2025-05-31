package Simulator;

import Output.OutputStrategy;
import model.Category;
import model.DiceRoll;

import java.util.List;

public class GameSimulator {
    private final OutputStrategy output;

    public GameSimulator(OutputStrategy output) {
        this.output = output;
    }

    public void simulateGivenRolls(List<int[]> rolls, List<Category> categories) {
        for (int i = 0; i < rolls.size(); i++) {
            DiceRoll diceRoll = new DiceRoll(rolls.get(i));
            Category chosenCategory = categories.get(i);
            int score = chosenCategory.score(diceRoll);
            String outcome = getOutcome(chosenCategory, score);

            output.print((i + 1) + ". ROLL");
            output.print("You've chosen " + chosenCategory + " as score category");
            output.print("Score: " + score);
            output.print("You've got " + outcome + "\n");
        }
    }

    public String getOutcome(Category category, int score) {
        if (score == 0) return "NOTHING";
        return switch (category) {
            case YATZY -> "a YATZY";
            case FULL_HOUSE -> "a FULL_HOUSE";
            case SMALL_STRAIGHT -> "a SMALL_STRAIGHT";
            case LARGE_STRAIGHT -> "a LARGE_STRAIGHT";
            case FOUR_OF_A_KIND -> "a FOUR_OF_A_KIND";
            case THREE_OF_A_KIND -> "a THREE_OF_A_KIND";
            case TWO_PAIRS -> "TWO_PAIRS";
            case PAIR -> "a PAIR";
            case ONES, TWOS, THREES, FOURS, FIVES, SIXES -> "some " + category.name();
            case CHANCE -> "a total score of " + score;
        };
    }
}
