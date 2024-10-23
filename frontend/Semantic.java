package frontend;

import common.Error;
import common.ErrorType;
import symbol.SymbolTable;
import syntaxNode.AddExp;
import syntaxNode.BType;
import syntaxNode.CompUnit;
import syntaxNode.ConstDecl;
import syntaxNode.ConstDef;
import syntaxNode.ConstInitVal;
import syntaxNode.Decl;
import syntaxNode.Exp;
import syntaxNode.FuncDef;
import syntaxNode.InitVal;
import syntaxNode.LVal;
import syntaxNode.MainFuncDef;
import syntaxNode.MulExp;
import syntaxNode.PrimaryExp;
import syntaxNode.UnaryExp;
import syntaxNode.VarDecl;
import syntaxNode.VarDef;
import util.SymbolFactory;

import java.util.List;

public class Semantic {

    private SymbolTable currentSymbolTable;

    private List<Error> errors;


    public Semantic(List<Error> errors) {
        this.currentSymbolTable = new SymbolTable();
        this.errors = errors;
    }

    public void fCompUnit(CompUnit compUnit) {
        // CompUnit -> {Decl} {FuncDef} MainFuncDef
        for (Decl decl : compUnit.getDecls()) {
            fDecl(decl);
        }
        for (FuncDef funcDef : compUnit.getFuncDefs()) {
            fFuncDef(funcDef);
        }
        fMainFunDef(compUnit.getMainFuncDef());
    }

    public void fDecl(Decl decl) {
        // Decl -> ConstDecl | VarDecl
        if (decl.getConstDecl() != null) {
            fConstDecl(decl.getConstDecl());
        } else {
            fVarDecl(decl.getVarDecl());
        }
    }

    public void fFuncDef(FuncDef funcDef) {
        // FuncDef -> FuncType Ident '(' ')' Block
        // FuncType -> BType | 'void'
    }

    public void fMainFunDef(MainFuncDef mainFuncDef) {
        // MainFuncDef -> 'void' 'main' '(' ')' Block

    }

    public void fConstDecl(ConstDecl constDecl) {
        // ConstDecl -> 'const' BType ConstDef {',' ConstDef} ';'
        for (ConstDef constDef : constDecl.getConstDefs()) {
            fConstDef(constDef, constDecl.getBType());
        }
    }

    public void fVarDecl(VarDecl varDecl) {
        // VarDecl -> BType VarDef {',' VarDef} ';'
        for (VarDef varDef : varDecl.getVarDefs()) {
            fVarDef(varDef, varDecl.getBType());
        }
    }

    public void fConstDef(ConstDef constDef, BType bType) {
        // ConstDef → Ident [ '[' ConstExp ']' ] '=' ConstInitVal
        Token ident = constDef.getIdent();
        if (currentSymbolTable.contains(ident.getContent())) {
            errors.add(new Error(ident.getLineNumber(), ErrorType.b));
        }
        int dimension = constDef.getDimension();
        if (dimension == 0) {
            currentSymbolTable.put(ident.getContent(), SymbolFactory.buildConst0(bType));
        } else {
            currentSymbolTable.put(ident.getContent(), SymbolFactory.buildConst1(bType));
        }
        if (constDef.getConstInitVal() != null) {
            fConstInitVal(constDef.getConstInitVal());
        }
    }

    public void fVarDef(VarDef varDef, BType bType) {
        // VarDef → Ident [ '[' ConstExp ']' ] | Ident [ '[' ConstExp ']' ] '=' InitVal
        Token ident = varDef.getIdent();
        if (currentSymbolTable.contains(ident.getContent())) {
            errors.add(new Error(ident.getLineNumber(), ErrorType.b));
        }
        int dimension = varDef.getDimension();
        if (dimension == 0) {
            currentSymbolTable.put(ident.getContent(), SymbolFactory.buildVar0(bType));
        } else {
            currentSymbolTable.put(ident.getContent(), SymbolFactory.buildVar1(bType));
        }
        if (varDef.getInitVal() != null) {
            fInitVal(varDef.getInitVal());
        }
    }

    public void fInitVal(InitVal initVal) {
        // InitVal → Exp | '{' [ InitVal { ',' InitVal } ] '}'
    }

    public void fConstInitVal(ConstInitVal constInitVal) {
        // ConstInitVal → ConstExp | '{' [ ConstInitVal { ',' ConstInitVal } ] '}'
    }


    public void fExp(Exp exp) {
        // Exp → AddExp [ ( '==' | '!=' | '<' | '<=' | '>' | '>=' ) AddExp ]
    }

    public void fAddExp(AddExp addExp) {
        // AddExp → MulExp { ('+' | '-') MulExp }
    }

    public void fMulExp(MulExp mulExp) {
        // MulExp → UnaryExp { ('*' | '/' | '%') UnaryExp }
    }

    public void fUnaryExp(UnaryExp unaryExp) {
        // UnaryExp → PrimaryExp | '++' UnaryExp | '--' UnaryExp | UnaryOp UnaryExp
    }

    public void fPrimaryExp(PrimaryExp primaryExp) {
        // PrimaryExp → '(' Exp ')' | LVal | Number | FuncRParams
    }

    public void fLVal(LVal lVal) {
        // LVal → Ident | Ident '[' Exp ']'
    }

    public void fUnaryOp() {
        // UnaryOp → '+' | '-' | '!' | '&'
    }

    public void fFuncRParams() {
        // FuncRParams → '(' [ Exp { ',' Exp } ] ')'
    }

    public void fFuncFParams() {
        // FuncFParams → '(' [ FuncFParam { ',' FuncFParam } ] ')'
    }

    public void fFuncFParam() {
        // FuncFParam → BType Ident [ '[' ']' ]
    }

    public void fBlock() {
        // Block → '{' { BlockItem } '}'
    }

    public void fBlockItem() {
        // BlockItem → Decl | Stmt
    }

    public void fStmt() {
        // Stmt → LVal '=' Exp ';' | 'if' '(' Cond ')' Stmt [ 'else' Stmt ] | 'while' '(' Cond ')' Stmt | 'for' '(' [ Exp ] ';' [ Exp ] ';' [ Exp ] ')' Stmt | Block | 'break' ';' | 'continue' ';' | 'return' [ Exp ] ';'
    }

    public void fCond() {
        // Cond → LOrExp
    }

    public void fLOrExp() {
        // LOrExp → LAndExp { '||' LAndExp }
    }

    public void fLAndExp() {
        // LAndExp → EqExp { '&&' EqExp }
    }

    public void fEqExp() {
        // EqExp → RelExp { ('==' | '!=') RelExp }
    }

    public void fRelExp() {
        // RelExp → AddExp { ('<' | '<=' | '>' | '>=') AddExp }
    }

    public void fForStmt() {
        // ForStmt → 'for' '(' [ Exp ] ';' [ Exp ] ';' [ Exp ] ')' Stmt
    }

    public void fNumber() {
        // Number → [ '+' | '-' ] INT_CONST
    }

    public void fCharacter() {
        // Character → CHAR_CONST
    }



}
