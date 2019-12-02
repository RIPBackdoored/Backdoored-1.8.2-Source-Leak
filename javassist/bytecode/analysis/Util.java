package javassist.bytecode.analysis;

import javassist.bytecode.*;

public class Util implements Opcode
{
    public Util() {
        super();
    }
    
    public static int getJumpTarget(int a1, final CodeIterator a2) {
        final int v1 = a2.byteAt(a1);
        a1 += ((v1 == 201 || v1 == 200) ? a2.s32bitAt(a1 + 1) : a2.s16bitAt(a1 + 1));
        return a1;
    }
    
    public static boolean isJumpInstruction(final int a1) {
        return (a1 >= 153 && a1 <= 168) || a1 == 198 || a1 == 199 || a1 == 201 || a1 == 200;
    }
    
    public static boolean isGoto(final int a1) {
        return a1 == 167 || a1 == 200;
    }
    
    public static boolean isJsr(final int a1) {
        return a1 == 168 || a1 == 201;
    }
    
    public static boolean isReturn(final int a1) {
        return a1 >= 172 && a1 <= 177;
    }
}
