import frontend.Lexer;
import frontend.Semantic;
import frontend.Syntax;
import frontend.Token;
import common.Error;
import util.IO;

import java.util.ArrayList;
import java.util.List;

public class Compiler {

    public static void main(String[] args) {
        String content = IO.dealStdin();
        List<Error> errors = new ArrayList<>();
        Lexer lexer = new Lexer(content, errors);
        List<Token> tokens = lexer.analyze();
        Syntax syntax = new Syntax(tokens, errors);
        syntax.analyze();
        Semantic semantic = new Semantic(errors);
        semantic.fCompUnit(syntax.getCompUnit());
        if (errors.size() == 0) {
            IO.dealStdout(syntax);
        } else{
            IO.dealStderr(errors);
        }
    }
}
