package syntaxNode;

import common.BasciNode;
import common.SyntaxType;
import frontend.Token;
import util.IO;

import static frontend.Parser.nodeMap;

public class PrimaryExp implements BasciNode {
    //  PrimaryExp → '(' Exp ')' | LVal | Number | Character

    private Token lparent;
    private Exp exp;
    private Token rparent;
    private LVal lVal;
    private Number number;
    private Character character;

    public PrimaryExp(Token lparent, Exp exp, Token rparent) {
        this.lparent = lparent;
        this.exp = exp;
        this.rparent = rparent;
    }

    public PrimaryExp(LVal lVal) {
        this.lVal = lVal;
    }

    public PrimaryExp(Number number) {
        this.number = number;
    }

    public PrimaryExp(Character character) {
        this.character = character;
    }

    @Override
    public void print() {
        if (lparent != null) {
            IO.dealParseOut(lparent.toString());
            exp.print();
            IO.dealParseOut(rparent.toString());
        } else if (lVal != null) {
            lVal.print();
        } else if (number != null) {
            number.print();
        } else if (character != null) {
            character.print();
        }
        IO.dealParseOut(nodeMap.get(SyntaxType.PrimaryExp));
    }
}
