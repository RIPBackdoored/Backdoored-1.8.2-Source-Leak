package javassist.bytecode;

import java.io.*;
import java.util.*;

public class LocalVariableTypeAttribute extends LocalVariableAttribute
{
    public static final String tag = "LocalVariableTypeTable";
    
    public LocalVariableTypeAttribute(final ConstPool a1) {
        super(a1, "LocalVariableTypeTable", new byte[2]);
        ByteArray.write16bit(0, this.info, 0);
    }
    
    LocalVariableTypeAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    private LocalVariableTypeAttribute(final ConstPool a1, final byte[] a2) {
        super(a1, "LocalVariableTypeTable", a2);
    }
    
    @Override
    String renameEntry(final String a1, final String a2, final String a3) {
        return SignatureAttribute.renameClass(a1, a2, a3);
    }
    
    @Override
    String renameEntry(final String a1, final Map a2) {
        return SignatureAttribute.renameClass(a1, a2);
    }
    
    @Override
    LocalVariableAttribute makeThisAttr(final ConstPool a1, final byte[] a2) {
        return new LocalVariableTypeAttribute(a1, a2);
    }
}
