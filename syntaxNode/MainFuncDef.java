package syntaxNode;

import common.BasciNode;
import common.SyntaxType;
import frontend.Token;
import util.IO;

import static frontend.Parser.nodeMap;

public class MainFuncDef implements BasciNode {
    //  MainFuncDef → 'int' 'main' '(' ')' Block

    private Token inttk;
    private Token maintk;
    private Token lparent;
    private Token rparent;
    private Block block;

    public MainFuncDef(Token inttk, Token maintk, Token lparent, Token rparent, Block block) {
        this.inttk = inttk;
        this.maintk = maintk;
        this.lparent = lparent;
        this.rparent = rparent;
        this.block = block;
    }

    @Override
    public void print() {
        IO.dealParseOut(inttk.toString());
        IO.dealParseOut(maintk.toString());
        IO.dealParseOut(lparent.toString());
        IO.dealParseOut(rparent.toString());
        block.print();
        IO.dealParseOut(nodeMap.get(SyntaxType.MainFuncDef));
    }
}
