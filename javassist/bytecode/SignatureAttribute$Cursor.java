package javassist.bytecode;

private static class Cursor
{
    int position;
    
    private Cursor() {
        super();
        this.position = 0;
    }
    
    int indexOf(final String a1, final int a2) throws BadBytecode {
        final int v1 = a1.indexOf(a2, this.position);
        if (v1 < 0) {
            throw SignatureAttribute.access$000(a1);
        }
        this.position = v1 + 1;
        return v1;
    }
    
    Cursor(final SignatureAttribute$1 a1) {
        this();
    }
}
