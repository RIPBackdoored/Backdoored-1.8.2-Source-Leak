package javassist.bytecode;

static class LdcEntry
{
    LdcEntry next;
    int where;
    int index;
    
    LdcEntry() {
        super();
    }
    
    static byte[] doit(byte[] a1, final LdcEntry a2, final ExceptionTable a3, final CodeAttribute a4) throws BadBytecode {
        if (a2 != null) {
            a1 = CodeIterator.changeLdcToLdcW(a1, a3, a4, a2);
        }
        return a1;
    }
}
