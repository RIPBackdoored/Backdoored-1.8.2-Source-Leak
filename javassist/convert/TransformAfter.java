package javassist.convert;

import javassist.*;
import javassist.bytecode.*;

public class TransformAfter extends TransformBefore
{
    public TransformAfter(final Transformer a1, final CtMethod a2, final CtMethod a3) throws NotFoundException {
        super(a1, a2, a3);
    }
    
    @Override
    protected int match2(int a1, final CodeIterator a2) throws BadBytecode {
        a2.move(a1);
        a2.insert(this.saveCode);
        a2.insert(this.loadCode);
        int v1 = a2.insertGap(3);
        a2.setMark(v1);
        a2.insert(this.loadCode);
        a1 = a2.next();
        v1 = a2.getMark();
        a2.writeByte(a2.byteAt(a1), v1);
        a2.write16bit(a2.u16bitAt(a1 + 1), v1 + 1);
        a2.writeByte(184, a1);
        a2.write16bit(this.newIndex, a1 + 1);
        a2.move(v1);
        return a2.next();
    }
}
