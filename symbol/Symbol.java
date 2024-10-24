package symbol;

import common.SemanticType;

public class Symbol {

    private SemanticType type;    // 符号对应的字符串

    private int dimension; // 0 var，1 array

    private int isConst;// 0 变量，1 常量

    private int bType; // 0 int, 1 char

    private int retType; // 0 int, 1 char, 2 void

    private int paramNum; // 参数个数

    private int[] paramType; // 参数类型 0 int, 1 char

    public SemanticType getType() {
        return type;
    }

    public Symbol() {

    }

    public Symbol(SemanticType type, int dimension, int isConst, int bType) {
        this.type = type;
        this.dimension = dimension;
        this.isConst = isConst;
        this.bType = bType;
    }

    public Symbol(SemanticType type, int retType, int paramNum, int[] paramType) {
        this.type = type;
        this.retType = retType;
        this.paramNum = paramNum;
        this.paramType = paramType;
    }

}
