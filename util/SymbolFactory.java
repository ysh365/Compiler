package util;

import common.SemanticType;
import symbol.Symbol;
import syntaxNode.BType;

// 1代表数组，0代表变量
public class  SymbolFactory {

    public static Symbol buildConst0(BType bType) {
        if (bType.getToken().toString().equals("int")) {
            return new Symbol(SemanticType.ConstInt, 0, 1, 0);
        } else {
            return new Symbol(SemanticType.ConstChar, 0, 1, 1);
        }
    }

    public static Symbol buildConst1(BType bType) {
        if (bType.getToken().toString().equals("int")) {
            return new Symbol(SemanticType.ConstIntArray, 1, 1, 0);
        } else {
            return new Symbol(SemanticType.ConstCharArray, 1, 1, 1);
        }
    }

    public static Symbol buildVar0(BType bType) {
        if (bType.getToken().toString().equals("int")) {
            return new Symbol(SemanticType.Int, 0, 0, 0);
        } else {
            return new Symbol(SemanticType.Char, 0, 0, 1);
        }
    }

    public static Symbol buildVar1(BType bType) {
        if (bType.getToken().toString().equals("int")) {
            return new Symbol(SemanticType.IntArray, 1, 0, 0);
        } else {
            return new Symbol(SemanticType.CharArray, 1, 0, 1);
        }
    }

    public static Symbol buildFunc() {
        return new Symbol();
    }

}
