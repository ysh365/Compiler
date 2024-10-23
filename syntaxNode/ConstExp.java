package syntaxNode;

import common.BasciNode;
import common.SyntaxType;
import util.IO;

import static frontend.Syntax.nodeMap;

public class ConstExp implements BasciNode {
    // ConstExp → AddExp

    private AddExp addExp;

    public ConstExp(AddExp addExp) {
        this.addExp = addExp;
    }

    @Override
    public void print() {
        addExp.print();
        IO.dealSyntax(nodeMap.get(SyntaxType.ConstExp));
    }
}
