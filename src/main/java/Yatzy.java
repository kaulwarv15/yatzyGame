

import Output.ConsoleOutput;
import Output.FileOutput;
import Output.OutputStrategy;

import java.io.IOException;

public class Yatzy {
    public static void main(String[] args) throws IOException {
        OutputStrategy output = new ConsoleOutput();
        output.print("Console Simulation:");
        OutputStrategy fileOut = new FileOutput("yatzy_output.txt");
    }
}
