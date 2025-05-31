import Output.ConsoleOutput;
import Output.FileOutput;
import Output.OutputStrategy;
import Simulator.GameSimulator;
import model.Category;

import java.io.IOException;
import java.util.List;

public class Yatzy {
    public static void main(String[] args) throws IOException {
        OutputStrategy output = new ConsoleOutput();
        GameSimulator simulator = new GameSimulator(output);


        List<int[]> rolls = List.of(
                new int[]{1, 1, 1, 1, 1},
                new int[]{3, 3, 3, 2, 5},
                new int[]{1, 2, 3, 4, 5}
        );

        List<Category> categories = List.of(
                Category.YATZY,
                Category.THREE_OF_A_KIND,
                Category.SMALL_STRAIGHT
        );

        OutputStrategy consoleOut = new ConsoleOutput();
        consoleOut.print("Console Simulation:");
        new GameSimulator(consoleOut).simulateGivenRolls(rolls, categories);

        OutputStrategy fileOut = new FileOutput("yatzy_output.txt");
        new GameSimulator(fileOut).simulateGivenRolls(rolls, categories);
        if (fileOut instanceof FileOutput) {
            ((FileOutput) fileOut).close(); // Ensure flushing & writing
        }
    }
    }

