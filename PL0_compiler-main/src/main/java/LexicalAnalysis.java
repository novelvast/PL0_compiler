import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalysis {
    public void get_output() {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {

            String line;
            Pattern pattern = Pattern.compile("\\b(PROGRAM|BEGIN|END|CONST|VAR|WHILE|DO|IF|THEN)\\b|[a-zA-Z]\\w*|\\d+|:=|==|<>|<=|>=|[-+*/=<>();,]");

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    String token = matcher.group();
                    String tokenType;

                    if (token.matches("\\b(PROGRAM|BEGIN|END|CONST|VAR|WHILE|DO|IF|THEN)\\b")) {
                        tokenType = token; // Use the keyword as the token type
                    } else if (token.matches("[a-zA-Z]\\w*")) {
                        tokenType = "IDENT";
                    } else if (token.matches("\\d+")) {
                        tokenType = "NUMBER";
                    } else if (token.matches(":=")) {
                        tokenType = "ASSIGN";
                    } else if (token.matches("==")) {
                        tokenType = "EQUAL";
                    } else if (token.matches("<>")) {
                        tokenType = "NOT_EQUAL";
                    } else if (token.matches("<=")) {
                        tokenType = "LESS_EQUAL";
                    } else if (token.matches(">=")) {
                        tokenType = "GREATER_EQUAL";
                    } else if (token.matches("[-]")) {
                        tokenType = "MINUS";
                    } else if (token.matches("[+]")) {
                        tokenType = "ADD";
                    } else if (token.matches("[*]")) {
                        tokenType = "MUL";
                    } else if (token.matches("[/]")) {
                        tokenType = "DIVIDE";
                    } else if (token.matches("[=]")) {
                        tokenType = "EQUAL";
                    } else if (token.matches("[<]")) {
                        tokenType = "LESS";
                    } else if (token.matches("[>]")) {
                        tokenType = "GREATER";
                    } else if (token.matches("[(]")) {
                        tokenType = "LPAREN";
                    } else if (token.matches("[)]")) {
                        tokenType = "RPAREN";
                    } else if (token.matches("[;]")) {
                        tokenType = "SEMICOLON";
                    } else if (token.matches("[,]")) {
                        tokenType = "COMMA";
                    } else {
                        tokenType = "UNKNOWN";
                    }

                    writer.write(String.format("(%s,%s)%n", tokenType, token));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
