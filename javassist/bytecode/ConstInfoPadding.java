package javassist.bytecode;

import java.util.*;
import java.io.*;

class ConstInfoPadding extends ConstInfo
{
    public ConstInfoPadding(final int a1) {
        super(a1);
    }
    
    @Override
    public int getTag() {
        return 0;
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        return a2.addConstInfoPadding();
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.println("padding");
    }
}
