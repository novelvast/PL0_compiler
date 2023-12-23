import java.io.PrintWriter;
import java.nio.file.*;
import java.util.ArrayList;

public class DeclarationTableParser {
    private static int Level = 0;  // 初始深度，即为作业中的BLOCK
    private static int Adr = 0;  // 初始地址，即为作业中的DX
    private static ArrayList<Declaration> declarationList = new ArrayList<Declaration>();  // 生成的declaration list

    public static void add_declaration(Declaration declaration)
    {
        declarationList.add(declaration);
    }

    public static void create_table() throws Exception {
        // 检查output文件夹是否存在
        Path path = Paths.get("output");
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        PrintWriter writer = new PrintWriter("output/table.txt");
        for (Declaration declaration : declarationList) {
            writer.println(declaration.toString());
        }
        writer.close();
    }

    public static int getLevel() {
        return Level;
    }

    public static int getAdr() {
        return Adr++;
    }


}
