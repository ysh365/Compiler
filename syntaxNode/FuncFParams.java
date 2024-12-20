package syntaxNode;

import common.BasciNode;
import common.SyntaxType;
import common.Token;
import util.IO;

import java.util.List;

import static frontend.Syntax.nodeMap;

public class FuncFParams implements BasciNode {
    //  FuncFParams → FuncFParam { ',' FuncFParam }
    private List<FuncFParam> funcFParams;
    private List<Token> commas;

    public FuncFParams(List<FuncFParam> funcFParams, List<Token> commas) {
        this.funcFParams = funcFParams;
        this.commas = commas;
    }

    public List<FuncFParam> getFuncFParams() {
        return funcFParams;
    }

    @Override
    public void print() {
        funcFParams.get(0).print();
        for (int i = 0; i < commas.size(); i++) {
            IO.dealSyntax(commas.get(i).toString());
            funcFParams.get(i+1).print();
        }
        IO.dealSyntax(nodeMap.get(SyntaxType.FuncFParams));
    }
}
