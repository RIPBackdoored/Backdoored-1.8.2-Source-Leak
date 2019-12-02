package javassist.bytecode;

import java.io.*;
import java.util.*;

public class SourceFileAttribute extends AttributeInfo
{
    public static final String tag = "SourceFile";
    
    SourceFileAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    public SourceFileAttribute(final ConstPool a1, final String a2) {
        super(a1, "SourceFile");
        final int v1 = a1.addUtf8Info(a2);
        final byte[] v2 = { (byte)(v1 >>> 8), (byte)v1 };
        this.set(v2);
    }
    
    public String getFileName() {
        return this.getConstPool().getUtf8Info(ByteArray.readU16bit(this.get(), 0));
    }
    
    @Override
    public AttributeInfo copy(final ConstPool a1, final Map a2) {
        return new SourceFileAttribute(a1, this.getFileName());
    }
}
