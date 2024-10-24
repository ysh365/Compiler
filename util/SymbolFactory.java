package util;

import common.SemanticType;
import symbol.Symbol;
import syntaxNode.BType;
import syntaxNode.FuncFParams;
import syntaxNode.FuncType;

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

    public static Symbol buildFunc(FuncType funcType, FuncFParams funcFParams) {
        int paramNum = funcFParams.getFuncFParams().size();
        int[] paramType = new int[paramNum];
        for (int i = 0; i < paramNum; i++) {
            if (funcFParams.getFuncFParams().get(i).getBType().getToken().toString().equals("int")) {
                paramType[i] = 0;
            } else {
                paramType[i] = 1;
            }
        }
        if (funcType.getToken().toString().equals("int")) {
            return new Symbol(SemanticType.IntFunc, 0, paramNum, paramType);
        } else if (funcType.getToken().toString().equals("char")) {
            return new Symbol(SemanticType.CharFunc, 1, paramNum, paramType);
        } else {
            return new Symbol(SemanticType.VoidFunc, 2, paramNum, paramType);
        }
    }

}
