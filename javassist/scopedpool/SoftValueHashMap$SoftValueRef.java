package javassist.scopedpool;

import java.lang.ref.*;

private static class SoftValueRef extends SoftReference
{
    public Object key;
    
    private SoftValueRef(final Object a1, final Object a2, final ReferenceQueue a3) {
        super(a2, a3);
        this.key = a1;
    }
    
    private static SoftValueRef create(final Object a1, final Object a2, final ReferenceQueue a3) {
        if (a2 == null) {
            return null;
        }
        return new SoftValueRef(a1, a2, a3);
    }
    
    static /* bridge */ SoftValueRef access$000(final Object a1, final Object a2, final ReferenceQueue a3) {
        return create(a1, a2, a3);
    }
}
