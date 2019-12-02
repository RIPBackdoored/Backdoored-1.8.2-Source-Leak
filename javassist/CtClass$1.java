package javassist;

class CtClass$1 extends ClassMap {
    final /* synthetic */ CtClass this$0;
    
    CtClass$1(final CtClass a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public void put(final String a1, final String a2) {
        this.put0(a1, a2);
    }
    
    @Override
    public Object get(final Object a1) {
        final String v1 = ClassMap.toJavaName((String)a1);
        this.put0(v1, v1);
        return null;
    }
    
    @Override
    public void fix(final String a1) {
    }
}