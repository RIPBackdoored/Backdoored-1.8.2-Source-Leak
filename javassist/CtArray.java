package javassist;

final class CtArray extends CtClass
{
    protected ClassPool pool;
    private CtClass[] interfaces;
    
    CtArray(final String a1, final ClassPool a2) {
        super(a1);
        this.interfaces = null;
        this.pool = a2;
    }
    
    @Override
    public ClassPool getClassPool() {
        return this.pool;
    }
    
    @Override
    public boolean isArray() {
        return true;
    }
    
    @Override
    public int getModifiers() {
        int v1 = 16;
        try {
            v1 |= (this.getComponentType().getModifiers() & 0x7);
        }
        catch (NotFoundException ex) {}
        return v1;
    }
    
    @Override
    public CtClass[] getInterfaces() throws NotFoundException {
        if (this.interfaces == null) {
            final Class[] v0 = Object[].class.getInterfaces();
            this.interfaces = new CtClass[v0.length];
            for (int v2 = 0; v2 < v0.length; ++v2) {
                this.interfaces[v2] = this.pool.get(v0[v2].getName());
            }
        }
        return this.interfaces;
    }
    
    @Override
    public boolean subtypeOf(final CtClass v2) throws NotFoundException {
        if (super.subtypeOf(v2)) {
            return true;
        }
        final String v3 = v2.getName();
        if (v3.equals("java.lang.Object")) {
            return true;
        }
        final CtClass[] v4 = this.getInterfaces();
        for (int a1 = 0; a1 < v4.length; ++a1) {
            if (v4[a1].subtypeOf(v2)) {
                return true;
            }
        }
        return v2.isArray() && this.getComponentType().subtypeOf(v2.getComponentType());
    }
    
    @Override
    public CtClass getComponentType() throws NotFoundException {
        final String v1 = this.getName();
        return this.pool.get(v1.substring(0, v1.length() - 2));
    }
    
    @Override
    public CtClass getSuperclass() throws NotFoundException {
        return this.pool.get("java.lang.Object");
    }
    
    @Override
    public CtMethod[] getMethods() {
        try {
            return this.getSuperclass().getMethods();
        }
        catch (NotFoundException v1) {
            return super.getMethods();
        }
    }
    
    @Override
    public CtMethod getMethod(final String a1, final String a2) throws NotFoundException {
        return this.getSuperclass().getMethod(a1, a2);
    }
    
    @Override
    public CtConstructor[] getConstructors() {
        try {
            return this.getSuperclass().getConstructors();
        }
        catch (NotFoundException v1) {
            return super.getConstructors();
        }
    }
}
