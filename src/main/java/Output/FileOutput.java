package Output;

import java.io.*;

public class FileOutput implements OutputStrategy {
    private final PrintWriter writer;

    public FileOutput(String filename) throws IOException {
        this.writer = new PrintWriter(new FileWriter(filename));
    }

    @Override
    public void print(String message) {
        writer.println(message);
    }

    public void close() {
        writer.close();
    }
}
