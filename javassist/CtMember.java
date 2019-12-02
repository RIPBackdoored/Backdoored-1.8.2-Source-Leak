package javassist;

public abstract class CtMember
{
    CtMember next;
    protected CtClass declaringClass;
    
    protected CtMember(final CtClass a1) {
        super();
        this.declaringClass = a1;
        this.next = null;
    }
    
    final CtMember next() {
        return this.next;
    }
    
    void nameReplaced() {
    }
    
    @Override
    public String toString() {
        final StringBuffer v1 = new StringBuffer(this.getClass().getName());
        v1.append("@");
        v1.append(Integer.toHexString(this.hashCode()));
        v1.append("[");
        v1.append(Modifier.toString(this.getModifiers()));
        this.extendToString(v1);
        v1.append("]");
        return v1.toString();
    }
    
    protected abstract void extendToString(final StringBuffer p0);
    
    public CtClass getDeclaringClass() {
        return this.declaringClass;
    }
    
    public boolean visibleFrom(final CtClass v-1) {
        final int v0 = this.getModifiers();
        if (Modifier.isPublic(v0)) {
            return true;
        }
        if (Modifier.isPrivate(v0)) {
            return v-1 == this.declaringClass;
        }
        final String v2 = this.declaringClass.getPackageName();
        final String v3 = v-1.getPackageName();
        boolean v4 = false;
        if (v2 == null) {
            final boolean a1 = v3 == null;
        }
        else {
            v4 = v2.equals(v3);
        }
        if (!v4 && Modifier.isProtected(v0)) {
            return v-1.subclassOf(this.declaringClass);
        }
        return v4;
    }
    
    public abstract int getModifiers();
    
    public abstract void setModifiers(final int p0);
    
    public boolean hasAnnotation(final Class a1) {
        return this.hasAnnotation(a1.getName());
    }
    
    public abstract boolean hasAnnotation(final String p0);
    
    public abstract Object getAnnotation(final Class p0) throws ClassNotFoundException;
    
    public abstract Object[] getAnnotations() throws ClassNotFoundException;
    
    public abstract Object[] getAvailableAnnotations();
    
    public abstract String getName();
    
    public abstract String getSignature();
    
    public abstract String getGenericSignature();
    
    public abstract void setGenericSignature(final String p0);
    
    public abstract byte[] getAttribute(final String p0);
    
    public abstract void setAttribute(final String p0, final byte[] p1);
    
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
}
