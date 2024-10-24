package symbol;

import java.util.HashMap;

public class SymbolTable {
    private int index; // 符号表编号

    private SymbolTable parent = null;  // 父符号表

    public HashMap<String, Symbol> symbols = new HashMap<>(); // 查找表

    public SymbolTable() {
    }

    public SymbolTable(int index, SymbolTable parent) {
        this.parent = parent;
        this.index = index;
    }

    public Symbol get(String ident) {
        Symbol symbol = symbols.get(ident);
        if (symbol != null) {
            return symbol;
        } else if (parent != null) {
            return parent.get(ident);
        }
        return null;
    }

    public boolean contains(String ident) {
        if (symbols.containsKey(ident)) {
            return true;
        } else if (parent != null) {
            return parent.contains(ident);
        }
        return false;
    }

    public void put(String ident, Symbol symbol) {
        symbols.put(ident, symbol);
    }

}
