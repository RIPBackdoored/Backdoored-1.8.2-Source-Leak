package javassist.bytecode.analysis;

import java.util.*;
import javassist.bytecode.*;

public class SubroutineScanner implements Opcode
{
    private Subroutine[] subroutines;
    Map subTable;
    Set done;
    
    public SubroutineScanner() {
        super();
        this.subTable = new HashMap();
        this.done = new HashSet();
    }
    
    public Subroutine[] scan(final MethodInfo v-3) throws BadBytecode {
        final CodeAttribute codeAttribute = v-3.getCodeAttribute();
        final CodeIterator iterator = codeAttribute.iterator();
        this.subroutines = new Subroutine[codeAttribute.getCodeLength()];
        this.subTable.clear();
        this.done.clear();
        this.scan(0, iterator, null);
        final ExceptionTable v0 = codeAttribute.getExceptionTable();
        for (int v2 = 0; v2 < v0.size(); ++v2) {
            final int a1 = v0.handlerPc(v2);
            this.scan(a1, iterator, this.subroutines[v0.startPc(v2)]);
        }
        return this.subroutines;
    }
    
    private void scan(int a1, final CodeIterator a2, final Subroutine a3) throws BadBytecode {
        if (this.done.contains(new Integer(a1))) {
            return;
        }
        this.done.add(new Integer(a1));
        final int v1 = a2.lookAhead();
        a2.move(a1);
        boolean v2;
        do {
            a1 = a2.next();
            v2 = (this.scanOp(a1, a2, a3) && a2.hasNext());
        } while (v2);
        a2.move(v1);
    }
    
    private boolean scanOp(final int v1, final CodeIterator v2, final Subroutine v3) throws BadBytecode {
        this.subroutines[v1] = v3;
        final int v4 = v2.byteAt(v1);
        if (v4 == 170) {
            this.scanTableSwitch(v1, v2, v3);
            return false;
        }
        if (v4 == 171) {
            this.scanLookupSwitch(v1, v2, v3);
            return false;
        }
        if (Util.isReturn(v4) || v4 == 169 || v4 == 191) {
            return false;
        }
        if (Util.isJumpInstruction(v4)) {
            final int a2 = Util.getJumpTarget(v1, v2);
            if (v4 == 168 || v4 == 201) {
                Subroutine a3 = this.subTable.get(new Integer(a2));
                if (a3 == null) {
                    a3 = new Subroutine(a2, v1);
                    this.subTable.put(new Integer(a2), a3);
                    this.scan(a2, v2, a3);
                }
                else {
                    a3.addCaller(v1);
                }
            }
            else {
                this.scan(a2, v2, v3);
                if (Util.isGoto(v4)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void scanLookupSwitch(final int a3, final CodeIterator v1, final Subroutine v2) throws BadBytecode {
        int v3 = (a3 & 0xFFFFFFFC) + 4;
        this.scan(a3 + v1.s32bitAt(v3), v1, v2);
        v3 += 4;
        final int v4 = v1.s32bitAt(v3);
        final int n = v4 * 8;
        for (v3 += 4, final int v5 = n + v3, v3 += 4; v3 < v5; v3 += 8) {
            final int a4 = v1.s32bitAt(v3) + a3;
            this.scan(a4, v1, v2);
        }
    }
    
    private void scanTableSwitch(final int a3, final CodeIterator v1, final Subroutine v2) throws BadBytecode {
        int v3 = (a3 & 0xFFFFFFFC) + 4;
        this.scan(a3 + v1.s32bitAt(v3), v1, v2);
        v3 += 4;
        final int v4 = v1.s32bitAt(v3);
        v3 += 4;
        final int v5 = v1.s32bitAt(v3);
        final int n = (v5 - v4 + 1) * 4;
        v3 += 4;
        for (int v6 = n + v3; v3 < v6; v3 += 4) {
            final int a4 = v1.s32bitAt(v3) + a3;
            this.scan(a4, v1, v2);
        }
    }
}
