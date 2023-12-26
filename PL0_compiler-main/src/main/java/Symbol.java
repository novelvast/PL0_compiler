/**
 * 符号类
 */
public class Symbol {
    private String name;        // 变量、常量或过程名
    private String type;        // 常量、变量或过程
    private String value;       // 常量或变量的值
    private String level;       // 嵌套层次
    private String address;     // 相对于所在嵌套过程基地址的地址

    public Symbol(String name, String type, String value, String level, String address) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.level = level;
        this.address = address;
    }

    // 变量声明和过程声明使用的构造函数
    public Symbol(String name, String type, String level, String address) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
