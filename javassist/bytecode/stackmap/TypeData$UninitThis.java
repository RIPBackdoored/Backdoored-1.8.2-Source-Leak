package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import java.util.*;

public static class UninitThis extends UninitData
{
    UninitThis(final String a1) {
        super(-1, a1);
    }
    
    @Override
    public UninitData copy() {
        return new UninitThis(this.getName());
    }
    
    @Override
    public int getTypeTag() {
        return 6;
    }
    
    @Override
    public int getTypeData(final ConstPool a1) {
        return 0;
    }
    
    @Override
    String toString2(final HashSet a1) {
        return "uninit:this";
    }
}
