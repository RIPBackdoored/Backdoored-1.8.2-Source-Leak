package javassist;

import java.io.*;
import java.net.*;

final class ClassPoolTail
{
    protected ClassPathList pathList;
    
    public ClassPoolTail() {
        super();
        this.pathList = null;
    }
    
    @Override
    public String toString() {
        final StringBuffer v1 = new StringBuffer();
        v1.append("[class path: ");
        for (ClassPathList v2 = this.pathList; v2 != null; v2 = v2.next) {
            v1.append(v2.path.toString());
            v1.append(File.pathSeparatorChar);
        }
        v1.append(']');
        return v1.toString();
    }
    
    public synchronized ClassPath insertClassPath(final ClassPath a1) {
        this.pathList = new ClassPathList(a1, this.pathList);
        return a1;
    }
    
    public synchronized ClassPath appendClassPath(final ClassPath a1) {
        final ClassPathList v1 = new ClassPathList(a1, null);
        ClassPathList v2 = this.pathList;
        if (v2 == null) {
            this.pathList = v1;
        }
        else {
            while (v2.next != null) {
                v2 = v2.next;
            }
            v2.next = v1;
        }
        return a1;
    }
    
    public synchronized void removeClassPath(final ClassPath a1) {
        ClassPathList v1 = this.pathList;
        if (v1 != null) {
            if (v1.path == a1) {
                this.pathList = v1.next;
            }
            else {
                while (v1.next != null) {
                    if (v1.next.path == a1) {
                        v1.next = v1.next.next;
                    }
                    else {
                        v1 = v1.next;
                    }
                }
            }
        }
        a1.close();
    }
    
    public ClassPath appendSystemPath() {
        return this.appendClassPath(new ClassClassPath());
    }
    
    public ClassPath insertClassPath(final String a1) throws NotFoundException {
        return this.insertClassPath(makePathObject(a1));
    }
    
    public ClassPath appendClassPath(final String a1) throws NotFoundException {
        return this.appendClassPath(makePathObject(a1));
    }
    
    private static ClassPath makePathObject(final String v1) throws NotFoundException {
        final String v2 = v1.toLowerCase();
        if (v2.endsWith(".jar") || v2.endsWith(".zip")) {
            return new JarClassPath(v1);
        }
        final int v3 = v1.length();
        if (v3 > 2 && v1.charAt(v3 - 1) == '*' && (v1.charAt(v3 - 2) == '/' || v1.charAt(v3 - 2) == File.separatorChar)) {
            final String a1 = v1.substring(0, v3 - 2);
            return new JarDirClassPath(a1);
        }
        return new DirClassPath(v1);
    }
    
    void writeClassfile(final String a1, final OutputStream a2) throws NotFoundException, IOException, CannotCompileException {
        final InputStream v1 = this.openClassfile(a1);
        if (v1 == null) {
            throw new NotFoundException(a1);
        }
        try {
            copyStream(v1, a2);
        }
        finally {
            v1.close();
        }
    }
    
    InputStream openClassfile(final String v2) throws NotFoundException {
        ClassPathList v3 = this.pathList;
        InputStream v4 = null;
        NotFoundException v5 = null;
        while (v3 != null) {
            try {
                v4 = v3.path.openClassfile(v2);
            }
            catch (NotFoundException a1) {
                if (v5 == null) {
                    v5 = a1;
                }
            }
            if (v4 != null) {
                return v4;
            }
            v3 = v3.next;
        }
        if (v5 != null) {
            throw v5;
        }
        return null;
    }
    
    public URL find(final String a1) {
        ClassPathList v1 = this.pathList;
        URL v2 = null;
        while (v1 != null) {
            v2 = v1.path.find(a1);
            if (v2 != null) {
                return v2;
            }
            v1 = v1.next;
        }
        return null;
    }
    
    public static byte[] readStream(final InputStream v-5) throws IOException {
        final byte[][] array = new byte[8][];
        int n = 4096;
        for (int i = 0; i < 8; ++i) {
            array[i] = new byte[n];
            int j = 0;
            int v0 = 0;
            do {
                v0 = v-5.read(array[i], j, n - j);
                if (v0 < 0) {
                    final byte[] v2 = new byte[n - 4096 + j];
                    int v3 = 0;
                    for (int a1 = 0; a1 < i; ++a1) {
                        System.arraycopy(array[a1], 0, v2, v3, v3 + 4096);
                        v3 = v3 + v3 + 4096;
                    }
                    System.arraycopy(array[i], 0, v2, v3, j);
                    return v2;
                }
                j += v0;
            } while (j < n);
            n *= 2;
        }
        throw new IOException("too much data");
    }
    
    public static void copyStream(final InputStream v-3, final OutputStream v-2) throws IOException {
        int n = 4096;
        byte[] v0 = null;
        for (int v2 = 0; v2 < 64; ++v2) {
            if (v2 < 8) {
                n *= 2;
                v0 = new byte[n];
            }
            int a1 = 0;
            int a2 = 0;
            do {
                a2 = v-3.read(v0, a1, n - a1);
                if (a2 < 0) {
                    v-2.write(v0, 0, a1);
                    return;
                }
                a1 += a2;
            } while (a1 < n);
            v-2.write(v0);
        }
        throw new IOException("too much data");
    }
}
