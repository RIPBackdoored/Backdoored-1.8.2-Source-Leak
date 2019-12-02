package javassist.compiler;

import java.util.*;
import javassist.bytecode.*;

static class JsrHook extends ReturnHook
{
    ArrayList jsrList;
    CodeGen cgen;
    int var;
    
    JsrHook(final CodeGen a1) {
        super(a1);
        this.jsrList = new ArrayList();
        this.cgen = a1;
        this.var = -1;
    }
    
    private int getVar(final int a1) {
        if (this.var < 0) {
            this.var = this.cgen.getMaxLocals();
            this.cgen.incMaxLocals(a1);
        }
        return this.var;
    }
    
    private void jsrJmp(final Bytecode a1) {
        a1.addOpcode(167);
        this.jsrList.add(new int[] { a1.currentPc(), this.var });
        a1.addIndex(0);
    }
    
    @Override
    protected boolean doit(final Bytecode a1, final int a2) {
        switch (a2) {
            case 177: {
                this.jsrJmp(a1);
                break;
            }
            case 176: {
                a1.addAstore(this.getVar(1));
                this.jsrJmp(a1);
                a1.addAload(this.var);
                break;
            }
            case 172: {
                a1.addIstore(this.getVar(1));
                this.jsrJmp(a1);
                a1.addIload(this.var);
                break;
            }
            case 173: {
                a1.addLstore(this.getVar(2));
                this.jsrJmp(a1);
                a1.addLload(this.var);
                break;
            }
            case 175: {
                a1.addDstore(this.getVar(2));
                this.jsrJmp(a1);
                a1.addDload(this.var);
                break;
            }
            case 174: {
                a1.addFstore(this.getVar(1));
                this.jsrJmp(a1);
                a1.addFload(this.var);
                break;
            }
            default: {
                throw new RuntimeException("fatal");
            }
        }
        return false;
    }
}
