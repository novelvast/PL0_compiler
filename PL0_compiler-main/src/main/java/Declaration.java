public class Declaration {
    private String name = "";
    private String kind = "";
    private String val = "";
    private String level = "";
    private String adr = "";
    private int codeStartIndex = -1;

    public Declaration(String name, String kind, String val, String level, String adr) {
        this.name = name;
        this.kind = kind;
        this.val = val;
        this.level = level;
        this.adr = adr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public int getCodeStartIndex() {
        return codeStartIndex;
    }

    public void setCodeStartIndex(int codeStartIndex) {
        this.codeStartIndex = codeStartIndex;
    }

    @Override
    public String toString() {
        switch (kind) {
            case "CONSTANT":
                return "Declaration{" +
                        "name='" + name + '\'' +
                        ", kind='" + kind + '\'' +
                        ", val='" + val + '\'' +
                        '}';
            case "VARIABLE":
                return "Declaration{" +
                        "name='" + name + '\'' +
                        ", kind='" + kind + '\'' +
                        ", val='" + val + '\'' +
                        ", level='" + level + '\'' +
                        ", adr='" + adr + '\'' +
                        '}';
            default:
                return "Declaration{" +
                        "name='" + name + '\'' +
                        ", kind='" + kind + '\'' +
                        ", val='" + val + '\'' +
                        ", level='" + level + '\'' +
                        ", adr='" + adr + '\'' +
                        '}';
        }
    }
}
