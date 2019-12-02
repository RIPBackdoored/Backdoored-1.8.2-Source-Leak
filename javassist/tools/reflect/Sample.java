package javassist.tools.reflect;

public class Sample
{
    private Metaobject _metaobject;
    private static ClassMetaobject _classobject;
    
    public Sample() {
        super();
    }
    
    public Object trap(final Object[] a1, final int a2) throws Throwable {
        final Metaobject v1 = this._metaobject;
        if (v1 == null) {
            return ClassMetaobject.invoke(this, a2, a1);
        }
        return v1.trapMethodcall(a2, a1);
    }
    
    public static Object trapStatic(final Object[] a1, final int a2) throws Throwable {
        return Sample._classobject.trapMethodcall(a2, a1);
    }
    
    public static Object trapRead(final Object[] a1, final String a2) {
        if (a1[0] == null) {
            return Sample._classobject.trapFieldRead(a2);
        }
        return ((Metalevel)a1[0])._getMetaobject().trapFieldRead(a2);
    }
    
    public static Object trapWrite(final Object[] a1, final String a2) {
        final Metalevel v1 = (Metalevel)a1[0];
        if (v1 == null) {
            Sample._classobject.trapFieldWrite(a2, a1[1]);
        }
        else {
            v1._getMetaobject().trapFieldWrite(a2, a1[1]);
        }
        return null;
    }
}
