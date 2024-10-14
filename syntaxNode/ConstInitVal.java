package syntaxNode;

import common.BasciNode;
import common.SyntaxType;
import frontend.Token;
import util.IO;

import java.util.List;

import static frontend.Parser.nodeMap;

public class ConstInitVal implements BasciNode {
    //  ConstInitVal → ConstExp | '{' [ ConstExp { ',' ConstExp } ] '}' | StringConst

    private List<ConstExp> constExps;
    private List<Token> commas;
    private Token lbrace;
    private Token rbrace;
    private Token strcon;

    public ConstInitVal(Token strcon) {
        this.strcon = strcon;
    }

    public ConstInitVal(List<ConstExp> constExps) {
        this.constExps = constExps;
    }

    public ConstInitVal(List<ConstExp> constExps, List<Token> commas, Token lbrace, Token rbrace) {
        this.constExps = constExps;
        this.commas = commas;
        this.lbrace = lbrace;
        this.rbrace = rbrace;
    }

    @Override
    public void print() {
        if (strcon != null) {
            IO.dealParseOut(strcon.toString());
        } else if (lbrace != null) {
            IO.dealParseOut(lbrace.toString());
            if (!constExps.isEmpty()) {
                constExps.get(0).print();
            }
            for (int i=0; commas != null && i<commas.size(); i++) {
                IO.dealParseOut(commas.get(i).toString());
                constExps.get(i+1).print();
            }
            IO.dealParseOut(rbrace.toString());
        } else {
            constExps.get(0).print();
        }
        IO.dealParseOut(nodeMap.get(SyntaxType.ConstInitVal));
    }
}
