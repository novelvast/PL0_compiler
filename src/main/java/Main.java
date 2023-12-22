public class Main {

    private static final String lexerOutput = "output.txt";

    public static void main(String[] args) {

        Parser parser = new Parser(lexerOutput);
        parser.startParse();

    }
}
