package javassist.compiler;

import javassist.bytecode.*;

static class JsrHook2 extends ReturnHook
{
    int var;
    int target;
    
    JsrHook2(final CodeGen a1, final int[] a2) {
        super(a1);
        this.target = a2[0];
        this.var = a2[1];
    }
    
    @Override
    protected boolean doit(final Bytecode a1, final int a2) {
        switch (a2) {
            case 177: {
                break;
            }
            case 176: {
                a1.addAstore(this.var);
                break;
            }
            case 172: {
                a1.addIstore(this.var);
                break;
            }
            case 173: {
                a1.addLstore(this.var);
                break;
            }
            case 175: {
                a1.addDstore(this.var);
                break;
            }
            case 174: {
                a1.addFstore(this.var);
                break;
            }
            default: {
                throw new RuntimeException("fatal");
            }
        }
        a1.addOpcode(167);
        a1.addIndex(this.target - a1.currentPc() + 3);
        return true;
    }
}
