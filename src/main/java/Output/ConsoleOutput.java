package Output;

public class ConsoleOutput implements OutputStrategy {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
