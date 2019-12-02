package javassist.convert;

import javassist.*;
import javassist.bytecode.*;

public final class TransformWriteField extends TransformReadField
{
    public TransformWriteField(final Transformer a1, final CtField a2, final String a3, final String a4) {
        super(a1, a2, a3, a4);
    }
    
    @Override
    public int transform(final CtClass v-8, int v-7, final CodeIterator v-6, final ConstPool v-5) throws BadBytecode {
        final int byte1 = v-6.byteAt(v-7);
        if (byte1 == 181 || byte1 == 179) {
            final int u16bit = v-6.u16bitAt(v-7 + 1);
            final String field = TransformReadField.isField(v-8.getClassPool(), v-5, this.fieldClass, this.fieldname, this.isPrivate, u16bit);
            if (field != null) {
                if (byte1 == 179) {
                    final CodeAttribute a1 = v-6.get();
                    v-6.move(v-7);
                    final char a2 = field.charAt(0);
                    if (a2 == 'J' || a2 == 'D') {
                        v-7 = v-6.insertGap(3);
                        v-6.writeByte(1, v-7);
                        v-6.writeByte(91, v-7 + 1);
                        v-6.writeByte(87, v-7 + 2);
                        a1.setMaxStack(a1.getMaxStack() + 2);
                    }
                    else {
                        v-7 = v-6.insertGap(2);
                        v-6.writeByte(1, v-7);
                        v-6.writeByte(95, v-7 + 1);
                        a1.setMaxStack(a1.getMaxStack() + 1);
                    }
                    v-7 = v-6.next();
                }
                final int a3 = v-5.addClassInfo(this.methodClassname);
                final String a4 = "(Ljava/lang/Object;" + field + ")V";
                final int v1 = v-5.addMethodrefInfo(a3, this.methodName, a4);
                v-6.writeByte(184, v-7);
                v-6.write16bit(v1, v-7 + 1);
            }
        }
        return v-7;
    }
}
