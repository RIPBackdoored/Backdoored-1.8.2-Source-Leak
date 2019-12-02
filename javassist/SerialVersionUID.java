package javassist;

import java.util.*;
import java.io.*;
import java.security.*;
import javassist.bytecode.*;

public class SerialVersionUID
{
    public SerialVersionUID() {
        super();
    }
    
    public static void setSerialVersionUID(final CtClass a1) throws CannotCompileException, NotFoundException {
        try {
            a1.getDeclaredField("serialVersionUID");
        }
        catch (NotFoundException ex) {
            if (!isSerializable(a1)) {
                return;
            }
            final CtField v1 = new CtField(CtClass.longType, "serialVersionUID", a1);
            v1.setModifiers(26);
            a1.addField(v1, calculateDefault(a1) + "L");
        }
    }
    
    private static boolean isSerializable(final CtClass a1) throws NotFoundException {
        final ClassPool v1 = a1.getClassPool();
        return a1.subtypeOf(v1.get("java.io.Serializable"));
    }
    
    public static long calculateDefault(final CtClass v-7) throws CannotCompileException {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            final ClassFile classFile = v-7.getClassFile();
            final String javaName = javaName(v-7);
            dataOutputStream.writeUTF(javaName);
            final CtMethod[] declaredMethods = v-7.getDeclaredMethods();
            int modifiers = v-7.getModifiers();
            if ((modifiers & 0x200) != 0x0) {
                if (declaredMethods.length > 0) {
                    modifiers |= 0x400;
                }
                else {
                    modifiers &= 0xFFFFFBFF;
                }
            }
            dataOutputStream.writeInt(modifiers);
            final String[] v0 = classFile.getInterfaces();
            for (int a1 = 0; a1 < v0.length; ++a1) {
                v0[a1] = javaName(v0[a1]);
            }
            Arrays.sort(v0);
            for (int v2 = 0; v2 < v0.length; ++v2) {
                dataOutputStream.writeUTF(v0[v2]);
            }
            final CtField[] v3 = v-7.getDeclaredFields();
            Arrays.sort(v3, new Comparator() {
                SerialVersionUID$1() {
                    super();
                }
                
                @Override
                public int compare(final Object a1, final Object a2) {
                    final CtField v1 = (CtField)a1;
                    final CtField v2 = (CtField)a2;
                    return v1.getName().compareTo(v2.getName());
                }
            });
            for (int v4 = 0; v4 < v3.length; ++v4) {
                final CtField v5 = v3[v4];
                final int v6 = v5.getModifiers();
                if ((v6 & 0x2) == 0x0 || (v6 & 0x88) == 0x0) {
                    dataOutputStream.writeUTF(v5.getName());
                    dataOutputStream.writeInt(v6);
                    dataOutputStream.writeUTF(v5.getFieldInfo2().getDescriptor());
                }
            }
            if (classFile.getStaticInitializer() != null) {
                dataOutputStream.writeUTF("<clinit>");
                dataOutputStream.writeInt(8);
                dataOutputStream.writeUTF("()V");
            }
            final CtConstructor[] v7 = v-7.getDeclaredConstructors();
            Arrays.sort(v7, new Comparator() {
                SerialVersionUID$2() {
                    super();
                }
                
                @Override
                public int compare(final Object a1, final Object a2) {
                    final CtConstructor v1 = (CtConstructor)a1;
                    final CtConstructor v2 = (CtConstructor)a2;
                    return v1.getMethodInfo2().getDescriptor().compareTo(v2.getMethodInfo2().getDescriptor());
                }
            });
            for (int v8 = 0; v8 < v7.length; ++v8) {
                final CtConstructor v9 = v7[v8];
                final int v10 = v9.getModifiers();
                if ((v10 & 0x2) == 0x0) {
                    dataOutputStream.writeUTF("<init>");
                    dataOutputStream.writeInt(v10);
                    dataOutputStream.writeUTF(v9.getMethodInfo2().getDescriptor().replace('/', '.'));
                }
            }
            Arrays.sort(declaredMethods, new Comparator() {
                SerialVersionUID$3() {
                    super();
                }
                
                @Override
                public int compare(final Object a1, final Object a2) {
                    final CtMethod v1 = (CtMethod)a1;
                    final CtMethod v2 = (CtMethod)a2;
                    int v3 = v1.getName().compareTo(v2.getName());
                    if (v3 == 0) {
                        v3 = v1.getMethodInfo2().getDescriptor().compareTo(v2.getMethodInfo2().getDescriptor());
                    }
                    return v3;
                }
            });
            for (int v8 = 0; v8 < declaredMethods.length; ++v8) {
                final CtMethod v11 = declaredMethods[v8];
                final int v10 = v11.getModifiers() & 0xD3F;
                if ((v10 & 0x2) == 0x0) {
                    dataOutputStream.writeUTF(v11.getName());
                    dataOutputStream.writeInt(v10);
                    dataOutputStream.writeUTF(v11.getMethodInfo2().getDescriptor().replace('/', '.'));
                }
            }
            dataOutputStream.flush();
            final MessageDigest v12 = MessageDigest.getInstance("SHA");
            final byte[] v13 = v12.digest(byteArrayOutputStream.toByteArray());
            long v14 = 0L;
            for (int v15 = Math.min(v13.length, 8) - 1; v15 >= 0; --v15) {
                v14 = (v14 << 8 | (long)(v13[v15] & 0xFF));
            }
            return v14;
        }
        catch (IOException a2) {
            throw new CannotCompileException(a2);
        }
        catch (NoSuchAlgorithmException a3) {
            throw new CannotCompileException(a3);
        }
    }
    
    private static String javaName(final CtClass a1) {
        return Descriptor.toJavaName(Descriptor.toJvmName(a1));
    }
    
    private static String javaName(final String a1) {
        return Descriptor.toJavaName(Descriptor.toJvmName(a1));
    }
}
