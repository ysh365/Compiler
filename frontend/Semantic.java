package frontend;

import common.Error;
import common.ErrorType;
import symbol.SymbolTable;
import syntaxNode.AddExp;
import syntaxNode.BType;
import syntaxNode.Block;
import syntaxNode.BlockItem;
import syntaxNode.CompUnit;
import syntaxNode.Cond;
import syntaxNode.ConstDecl;
import syntaxNode.ConstDef;
import syntaxNode.ConstInitVal;
import syntaxNode.Decl;
import syntaxNode.EqExp;
import syntaxNode.Exp;
import syntaxNode.FuncDef;
import syntaxNode.FuncFParam;
import syntaxNode.FuncFParams;
import syntaxNode.InitVal;
import syntaxNode.LAndExp;
import syntaxNode.LOrExp;
import syntaxNode.LVal;
import syntaxNode.MainFuncDef;
import syntaxNode.MulExp;
import syntaxNode.PrimaryExp;
import syntaxNode.RelExp;
import syntaxNode.Stmt;
import syntaxNode.UnaryExp;
import syntaxNode.VarDecl;
import syntaxNode.VarDef;
import util.SymbolFactory;

import java.util.HashMap;
import java.util.List;

public class Semantic {

    private SymbolTable currentSymbolTable;

    private HashMap<Integer, SymbolTable> symbolTables;

    private List<Error> errors;

    int index;  // 符号表编号


    public Semantic(List<Error> errors) {
        index = 1;
        symbolTables = new HashMap<>();
        this.currentSymbolTable = new SymbolTable(1, null);
        symbolTables.put(index, currentSymbolTable);
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
        Token ident = funcDef.getIdent();
        if (currentSymbolTable.contains(ident.getContent())) {
            errors.add(new Error(ident.getLineNumber(), ErrorType.b));
        }
        currentSymbolTable.put(ident.getContent(), SymbolFactory.buildFunc(funcDef.getFuncType(), funcDef.getFuncFParams()));
        SymbolTable parent = currentSymbolTable;
        currentSymbolTable = new SymbolTable(++index, parent);
        if (funcDef.getFuncFParams() != null) {
            fFuncFParams(funcDef.getFuncFParams());
        }
        fBlock(funcDef.getBlock());
        currentSymbolTable = parent;
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
        // Exp → AddExp
         fAddExp(exp.getAddExp());
    }

    public void fAddExp(AddExp addExp) {
        // AddExp → MulExp { ('+' | '-') MulExp }
        for (MulExp mulExp : addExp.getMulExps()) {
            fMulExp(mulExp);
        }
    }

    public void fMulExp(MulExp mulExp) {
        // MulExp → UnaryExp { ('*' | '/' | '%') UnaryExp }
        for (UnaryExp unaryExp : mulExp.getUnaryExps()) {
            fUnaryExp(unaryExp);
        }
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

    public void fFuncFParams(FuncFParams funcFParams) {
        // FuncFParams → '(' [ FuncFParam { ',' FuncFParam } ] ')'
        for (FuncFParam funcFParam : funcFParams.getFuncFParams()) {
            fFuncFParam(funcFParam);
        }
    }

    public void fFuncFParam(FuncFParam funcFParam) {
        // FuncFParam → BType Ident [ '[' ']' ]
    }

    public void fBlock(Block block) {
        // Block → '{' { BlockItem } '}'
    }

    public void fBlockItem(BlockItem blockItem) {
        // BlockItem → Decl | Stmt
        if (blockItem.getDecl() != null) {
            fDecl(blockItem.getDecl());
        } else {
            fStmt(blockItem.getStmt());
        }
    }

    public void fStmt(Stmt stmt) {
        // Stmt → LVal '=' Exp ';' | 'if' '(' Cond ')' Stmt [ 'else' Stmt ] | 'while' '(' Cond ')' Stmt | 'for' '(' [ Exp ] ';' [ Exp ] ';' [ Exp ] ')' Stmt | Block | 'break' ';' | 'continue' ';' | 'return' [ Exp ] ';'
    }

    public void fForStmt() {
        // ForStmt → 'for' '(' [ Exp ] ';' [ Exp ] ';' [ Exp ] ')' Stmt
    }

    public void fCond(Cond cond) {
        // Cond → LOrExp
        fLOrExp(cond.getLOrExp());
    }

    public void fLOrExp(LOrExp lOrExp) {
        // LOrExp → LAndExp | LOrExp '||' LAndExp
        for (LAndExp lAndExp : lOrExp.getLAndExps()) {
            fLAndExp(lAndExp);
        }
    }

    public void fLAndExp(LAndExp lAndExp) {
        // LAndExp → EqExp | LAndExp '&&' EqExp
        for (EqExp eqExp : lAndExp.getEqExps()) {
            fEqExp(eqExp);
        }

    }

    public void fEqExp(EqExp eqExp) {
        //  EqExp → RelExp | EqExp ('==' | '!=') RelExp
        for (RelExp relExp : eqExp.getRelExps()) {
            fRelExp(relExp);
        }
    }

    public void fRelExp(RelExp relExp) {
        //  RelExp → AddExp | RelExp ('<' | '>' | '<=' | '>=') AddExp
         for (AddExp addExp : relExp.getAddExps()) {
            fAddExp(addExp);
         }
    }




}
