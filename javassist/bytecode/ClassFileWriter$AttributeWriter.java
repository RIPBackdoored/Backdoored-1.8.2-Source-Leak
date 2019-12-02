package javassist.bytecode;

import java.io.*;

public interface AttributeWriter
{
    int size();
    
    void write(final DataOutputStream p0) throws IOException;
}
