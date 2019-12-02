package javassist.bytecode;

public static class Iterator
{
    private String desc;
    private int index;
    private int curPos;
    private boolean param;
    
    public Iterator(final String a1) {
        super();
        this.desc = a1;
        final int n = 0;
        this.curPos = n;
        this.index = n;
        this.param = false;
    }
    
    public boolean hasNext() {
        return this.index < this.desc.length();
    }
    
    public boolean isParameter() {
        return this.param;
    }
    
    public char currentChar() {
        return this.desc.charAt(this.curPos);
    }
    
    public boolean is2byte() {
        final char v1 = this.currentChar();
        return v1 == 'D' || v1 == 'J';
    }
    
    public int next() {
        int v1 = this.index;
        char v2 = this.desc.charAt(v1);
        if (v2 == '(') {
            ++this.index;
            v2 = this.desc.charAt(++v1);
            this.param = true;
        }
        if (v2 == ')') {
            ++this.index;
            v2 = this.desc.charAt(++v1);
            this.param = false;
        }
        while (v2 == '[') {
            v2 = this.desc.charAt(++v1);
        }
        if (v2 == 'L') {
            v1 = this.desc.indexOf(59, v1) + 1;
            if (v1 <= 0) {
                throw new IndexOutOfBoundsException("bad descriptor");
            }
        }
        else {
            ++v1;
        }
        this.curPos = this.index;
        this.index = v1;
        return this.curPos;
    }
}
