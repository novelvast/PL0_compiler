import java.io.*;

/**
 * 语法分析器
 */
public class Parser {

    // 当前的token
    private Token token;

    // 符号表
    private SymbolTable symbolTable;

    private BufferedReader bufferedReader;

    /**
     * 构造函数
     * @param inputFilePath 输入文件路径
     */
    public Parser(String inputFilePath) {
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }


        symbolTable = new SymbolTable();
    }

    /**
     * 从输入流中获取下一个token，并保存在成员变量token中
     */
    private void getNextToken() {
        try {
            String line = bufferedReader.readLine();
            if (line != null) {
                int commaIndex = line.indexOf(",");

                String firstPart = line.substring(1, commaIndex).trim();
                String secondPart = line.substring(commaIndex + 1, line.length() - 1).trim();

                token = new Token(firstPart, secondPart);
                System.out.println(token.getTokenType() + " " + token.getTokenValue());

            } else {
                System.out.println("文件结束");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始语法分析
     */
    public void startParse() {
        programParser();
    }

    /**
     * <程序> := <程序首部><分程序>
     */
    private void programParser() {

        // 程序首部
        getNextToken();
        if (token.getTokenType() != TokenType.PROGRAM) {
           System.out.println("程序首部缺少关键字PROGRAM");
        }
        getNextToken();
        if (token.getTokenType() != TokenType.IDENT) {
            System.out.println("程序首部缺少标识符");
        }

        // 分程序
        subProgramParser();

    }

    /**
     * <分程序>::=[<常量说明>][<变量说明>]<语句>
     */
    private void subProgramParser() {
        getNextToken();
        if (token.getTokenType() == TokenType.CONST) {
            constDeclareParser();
            getNextToken();
        }
        if (token.getTokenType() == TokenType.VAR) {
            varDeclareParser();
            getNextToken();
        }

        statementParser();

    }


    /**
     * <常量说明> => CONST <常量定义>{, <常量定义>};
     */
    private void constDeclareParser() {
        constDefineParser();
        getNextToken();
        while (true) {
            // 多个逗号分隔的定义
            if (token.getTokenType() == TokenType.COMMA) {
                constDefineParser();
                getNextToken();
            }
            else break;
        }
        if (token.getTokenType() != TokenType.SEMICOLON) {
            System.out.println("常量说明格式错误，缺少分号");
        }
    }


    /**
     * <常量定义> => <标识符>=<无符号整数>
     */
    private void constDefineParser() {
        getNextToken();
        if (token.getTokenType() != TokenType.IDENT) {
            System.out.println("常量定义格式错误，缺少常量名");
        }
        String name = token.getTokenValue();
        // TODO:语义处理
        Declaration constDeclaration = new Declaration(name, "CONSTANT", "", DeclarationTableParser.getLevel()+"", DeclarationTableParser.getAdr()+"");

        getNextToken();
        if (token.getTokenType() != TokenType.EQUAL) {
            System.out.println("常量定义格式错误，缺少等号");
        }

        getNextToken();
        String valueStr = token.getTokenValue();
        int value = Integer.parseInt(valueStr);
        if (token.getTokenType() != TokenType.NUMBER) {
            System.out.println("常量定义格式错误，缺少常量值");
        }
        else{
            constDeclaration.setVal(valueStr);
        }
        // TODO:语义处理
        DeclarationTableParser.add_declaration(constDeclaration);
    }


    /**
     * <变量说明> => VAR<标识符>{,<标识符>};
     */
    private void varDeclareParser() {
        getNextToken();
        if (token.getTokenType() != TokenType.IDENT) {
            System.out.println("变量说明格式错误，缺少变量名");
        }
        String name = token.getTokenValue();
        // TODO:语义处理
        Declaration varDeclaration = new Declaration(name, "VARIABLE", "", DeclarationTableParser.getLevel()+"", DeclarationTableParser.getAdr()+"");

        getNextToken();
        while (true) {
            // 多个逗号分隔的定义
            if (token.getTokenType() == TokenType.COMMA) {
                DeclarationTableParser.add_declaration(varDeclaration);
                getNextToken();
                if (token.getTokenType() != TokenType.IDENT) {
                    System.out.println("变量说明格式错误，缺少变量名");
                }
                String name2 = token.getTokenValue();
                // TODO:语义处理
                varDeclaration = new Declaration(name2, "VARIABLE", "", DeclarationTableParser.getLevel()+"", DeclarationTableParser.getAdr()+"");
                getNextToken();
            }
            else if(token.getTokenType() == TokenType.EQUAL)
            {
                getNextToken();
                String valueStr = token.getTokenValue();
                int value = Integer.parseInt(valueStr);
                if (token.getTokenType() != TokenType.NUMBER) {
                    System.out.println("变量定义格式错误，缺少变量值");
                }
                else{
                    varDeclaration.setVal(valueStr);
                    getNextToken();
                }
            }
            else {
                DeclarationTableParser.add_declaration(varDeclaration);
                break;
            }
        }
        if (token.getTokenType() != TokenType.SEMICOLON) {
            System.out.println("变量说明格式错误，缺少分号");
        }
    }

    /**
     * 	<语句> => <赋值语句>|<条件语句>|<循环语句>|<复合语句>|<空语句>
     */
    private void statementParser() {
        switch (token.getTokenType()) {
            // 赋值语句
            case IDENT:
                assignStatParser();
                break;
                
            // 条件语句
            case IF:
                ifStatParser();
                break;

            // 循环语句
            case WHILE:
                whileStatParser();
                break;

            // 复合语句
            case BEGIN:
                compoundStatParser();
                break;
            case SEMICOLON:
            case END:

                break;
            default:
                System.out.println("语句类型错误");
        }
    }


    /**
     * <赋值语句> => <标识符> := <表达式>
     */
    private void assignStatParser() {
        String name = token.getTokenValue();

        getNextToken();
        if (token.getTokenType() != TokenType.ASSIGN) {
            System.out.println("赋值语句格式错误");
        }
        
        expressionParser();
    }

    /**
     * <表达式> => [+|-]项|<表达式><加法运算符><项>
     */
    private void expressionParser() {
        getNextToken();
        if (token.getTokenType() == TokenType.ADD || token.getTokenType() == TokenType.MINUS) {
            // TODO:语义处理

            getNextToken();
        }
        itemParser(); // 处理项

        while (true) {
            // 多个项
            if (token.getTokenType() == TokenType.ADD || token.getTokenType() == TokenType.MINUS) {
                // TODO:语义处理
                getNextToken();
                itemParser();
            }
            else
                break;
        }
    }

    /**
     * <项> => <因子>|<项><乘法运算符><因子>
     */
    private void itemParser() {
        factorParser(); // 处理因子
        getNextToken();
        while (true) {
            // 多个因子的情况
            if (token.getTokenType() == TokenType.MUL || token.getTokenType() == TokenType.DIVIDE) {
                // TODO:语义处理
                getNextToken();
                factorParser();
                getNextToken();
            } else
                break;
        }
    }

    /**
     * <因子> => <标识符>|<无符号整数>|(<表达式>)
     */
    private void factorParser() {
        // 标识符
        if (token.getTokenType() == TokenType.IDENT) {
            // TODO:语义处理

        }
        // 数字
        else if (token.getTokenType() == TokenType.NUMBER) {
            // TODO:语义处理

        }
        // 表达式
        else if (token.getTokenType() == TokenType.LPAREN) {
            expressionParser();

            if (token.getTokenType() != TokenType.RPAREN) {
                System.out.println("表达式缺少右括号");
            }

        }
        else {
            System.out.println("表达式缺少因子或因子格式错误");
        }
    }


    /**
     * <条件语句> => IF <条件> THEN <语句>
     */
    private void ifStatParser() {
        conditionParser(); // 处理条件

        if (token.getTokenType() != TokenType.THEN) {
            System.out.println("条件语句缺少THEN");
        }
        getNextToken();
        statementParser();
    }

    /**
     * <条件> => <表达式><关系运算符><表达式>
     */
    private void conditionParser() {
        expressionParser();
        switch (token.getTokenType()) {
            case EQUAL:
            case NOT_EQUAL:
            case LESS:
            case LESS_EQUAL:
            case GREATER:
            case GREATER_EQUAL:
                break;

            default:
                System.out.println("条件语句缺少关系运算符");
                break;
        }
        expressionParser();
    }


    /**
     * <循环语句> => WHILE <条件> DO <语句>
     */
    private void whileStatParser() {
        conditionParser();

        if (token.getTokenType() != TokenType.DO) {
            System.out.println("循环语句缺少DO");
        }
        getNextToken();
        statementParser();
    }


    /**
     * <复合语句> => BEGIN <语句>{;<语句>}END
     */
    private void compoundStatParser() {
        getNextToken();
        statementParser();
        while (true) {
            // 分号
            if (token.getTokenType() == TokenType.SEMICOLON) {
                getNextToken();
                statementParser();
            }
            // END
            else if (token.getTokenType() == TokenType.END) {

                break;
            }
            // 多个语句
            else {
                getNextToken();
                if (token.getTokenType() == TokenType.SEMICOLON) {
                    getNextToken();
                    statementParser();
                }
                else if (token.getTokenType() == TokenType.END) {
                    break;
                }
                else {
                    System.out.println("复合语句格式错误");
                }
            }
        }
    }


}
