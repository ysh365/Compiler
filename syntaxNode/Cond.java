package syntaxNode;

import common.BasciNode;
import common.SyntaxType;
import util.IO;

import static frontend.Syntax.nodeMap;

public class Cond implements BasciNode {
    // Cond → LOrExp

    private LOrExp lOrExp;

    public Cond(LOrExp lOrExp) {
        this.lOrExp = lOrExp;
    }

    @Override
    public void print() {
        lOrExp.print();
        IO.dealSyntax(nodeMap.get(SyntaxType.Cond));
    }
}
