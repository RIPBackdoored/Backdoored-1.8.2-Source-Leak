package javassist;

static class Cache extends CtMember
{
    private CtMember methodTail;
    private CtMember consTail;
    private CtMember fieldTail;
    
    @Override
    protected void extendToString(final StringBuffer a1) {
    }
    
    @Override
    public boolean hasAnnotation(final String a1) {
        return false;
    }
    
    @Override
    public Object getAnnotation(final Class a1) throws ClassNotFoundException {
        return null;
    }
    
    @Override
    public Object[] getAnnotations() throws ClassNotFoundException {
        return null;
    }
    
    @Override
    public byte[] getAttribute(final String a1) {
        return null;
    }
    
    @Override
    public Object[] getAvailableAnnotations() {
        return null;
    }
    
    @Override
    public int getModifiers() {
        return 0;
    }
    
    @Override
    public String getName() {
        return null;
    }
    
    @Override
    public String getSignature() {
        return null;
    }
    
    @Override
    public void setAttribute(final String a1, final byte[] a2) {
    }
    
    @Override
    public void setModifiers(final int a1) {
    }
    
    @Override
    public String getGenericSignature() {
        return null;
    }
    
    @Override
    public void setGenericSignature(final String a1) {
    }
    
    Cache(final CtClassType a1) {
        super(a1);
        this.methodTail = this;
        this.consTail = this;
        this.fieldTail = this;
        this.fieldTail.next = this;
    }
    
    CtMember methodHead() {
        return this;
    }
    
    CtMember lastMethod() {
        return this.methodTail;
    }
    
    CtMember consHead() {
        return this.methodTail;
    }
    
    CtMember lastCons() {
        return this.consTail;
    }
    
    CtMember fieldHead() {
        return this.consTail;
    }
    
    CtMember lastField() {
        return this.fieldTail;
    }
    
    void addMethod(final CtMember a1) {
        a1.next = this.methodTail.next;
        this.methodTail.next = a1;
        if (this.methodTail == this.consTail) {
            this.consTail = a1;
            if (this.methodTail == this.fieldTail) {
                this.fieldTail = a1;
            }
        }
        this.methodTail = a1;
    }
    
    void addConstructor(final CtMember a1) {
        a1.next = this.consTail.next;
        this.consTail.next = a1;
        if (this.consTail == this.fieldTail) {
            this.fieldTail = a1;
        }
        this.consTail = a1;
    }
    
    void addField(final CtMember a1) {
        a1.next = this;
        this.fieldTail.next = a1;
        this.fieldTail = a1;
    }
    
    static int count(CtMember a1, final CtMember a2) {
        int v1 = 0;
        while (a1 != a2) {
            ++v1;
            a1 = a1.next;
        }
        return v1;
    }
    
    void remove(final CtMember a1) {
        CtMember v1 = this;
        CtMember v2;
        while ((v2 = v1.next) != this) {
            if (v2 == a1) {
                v1.next = v2.next;
                if (v2 == this.methodTail) {
                    this.methodTail = v1;
                }
                if (v2 == this.consTail) {
                    this.consTail = v1;
                }
                if (v2 == this.fieldTail) {
                    this.fieldTail = v1;
                    break;
                }
                break;
            }
            else {
                v1 = v1.next;
            }
        }
    }
}
