import java.util.ArrayList;
import java.util.List;

/**
 * 符号表类
 */
public class SymbolTable {
    List<Symbol> symbolList;
    private int ptr = 0;

    public SymbolTable() {
        symbolList = new ArrayList<Symbol>();}

    //向符号表中插入常量
    public void insertConst(String name, String level, String value, String address) {
        symbolList.add(new Symbol(name, "CONST", value, level, address));
        ptr++;
    }

    //向符号表中插入变量
    public void insertVar(String name, String level, String address) {
        symbolList.add(new Symbol(name, "VAR", level, address));
        ptr++;
    }

    //向符号表中插入过程
    public void insertProc(String name, String level, String address) {
        symbolList.add(new Symbol(name, "PROCEDURE", level, address));
        ptr++;
    }


}
