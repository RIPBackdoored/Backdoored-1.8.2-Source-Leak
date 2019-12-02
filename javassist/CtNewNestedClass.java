package javassist;

import javassist.bytecode.*;

class CtNewNestedClass extends CtNewClass
{
    CtNewNestedClass(final String a1, final ClassPool a2, final boolean a3, final CtClass a4) {
        super(a1, a2, a3, a4);
    }
    
    @Override
    public void setModifiers(int a1) {
        a1 &= 0xFFFFFFF7;
        super.setModifiers(a1);
        updateInnerEntry(a1, this.getName(), this, true);
    }
    
    private static void updateInnerEntry(final int v-6, final String v-5, final CtClass v-4, final boolean v-3) {
        final ClassFile classFile2 = v-4.getClassFile2();
        final InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute)classFile2.getAttribute("InnerClasses");
        if (innerClassesAttribute == null) {
            return;
        }
        for (int v0 = innerClassesAttribute.tableLength(), v2 = 0; v2 < v0; ++v2) {
            if (v-5.equals(innerClassesAttribute.innerClass(v2))) {
                final int a3 = innerClassesAttribute.accessFlags(v2) & 0x8;
                innerClassesAttribute.setAccessFlags(v2, v-6 | a3);
                final String a4 = innerClassesAttribute.outerClass(v2);
                if (a4 == null || !v-3) {
                    break;
                }
                try {
                    final CtClass a5 = v-4.getClassPool().get(a4);
                    updateInnerEntry(v-6, v-5, a5, false);
                    break;
                }
                catch (NotFoundException a6) {
                    throw new RuntimeException("cannot find the declaring class: " + a4);
                }
            }
        }
    }
}
