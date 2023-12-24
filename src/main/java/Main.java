import java.util.ArrayList;

public class Main {

    private static final String lexerOutput = "output.txt";

    public static void main(String[] args) throws Exception {

        Parser parser = new Parser(lexerOutput);
        parser.startParse();
        DeclarationTableParser.create_table();
        Parser.create_table();
    }
}
