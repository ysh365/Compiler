package syntaxNode;

import common.BasciNode;
import common.SyntaxType;
import frontend.Token;
import util.IO;

import static frontend.Syntax.nodeMap;

public class FuncFParam implements BasciNode {
    // FuncFParam → BType Ident ['[' ']']
    private BType bType;
    private Token ident;
    private Token lbrack;
    private Token rbrack;

    public FuncFParam(BType bType, Token ident, Token lbrack, Token rbrack) {
        this.bType = bType;
        this.ident = ident;
        this.lbrack = lbrack;
        this.rbrack = rbrack;
    }

    public Token getIdent() {
        return ident;
    }

    public BType getBType() {
        return bType;
    }

    @Override
    public void print() {
        bType.print();
        IO.dealSyntax(ident.toString());
        if (lbrack != null) {
            IO.dealSyntax(lbrack.toString());
            IO.dealSyntax(rbrack.toString());
        }
        IO.dealSyntax(nodeMap.get(SyntaxType.FuncFParam));
    }
}
