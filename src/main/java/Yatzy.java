

import Output.ConsoleOutput;
import Output.FileOutput;
import Output.OutputStrategy;
import Simulator.GameSimulator;

import java.io.IOException;

public class Yatzy {
    public static void main(String[] args) throws IOException {
        OutputStrategy output = new ConsoleOutput();
        output.print("Console Simulation:");
        new GameSimulator(output).simulateGame(3);
        OutputStrategy fileOut = new FileOutput("yatzy_output.txt");
        new GameSimulator(fileOut).simulateGame(3);
    }
}
