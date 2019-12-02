package javassist.runtime;

public class Desc
{
    public static boolean useContextClassLoader;
    
    public Desc() {
        super();
    }
    
    private static Class getClassObject(final String a1) throws ClassNotFoundException {
        if (Desc.useContextClassLoader) {
            return Class.forName(a1, true, Thread.currentThread().getContextClassLoader());
        }
        return Class.forName(a1);
    }
    
    public static Class getClazz(final String v1) {
        try {
            return getClassObject(v1);
        }
        catch (ClassNotFoundException a1) {
            throw new RuntimeException("$class: internal error, could not find class '" + v1 + "' (Desc.useContextClassLoader: " + Boolean.toString(Desc.useContextClassLoader) + ")", a1);
        }
    }
    
    public static Class[] getParams(final String a1) {
        if (a1.charAt(0) != '(') {
            throw new RuntimeException("$sig: internal error");
        }
        return getType(a1, a1.length(), 1, 0);
    }
    
    public static Class getType(final String a1) {
        final Class[] v1 = getType(a1, a1.length(), 0, 0);
        if (v1 == null || v1.length != 1) {
            throw new RuntimeException("$type: internal error");
        }
        return v1[0];
    }
    
    private static Class[] getType(final String v-3, final int v-2, final int v-1, final int v0) {
        if (v-1 >= v-2) {
            return new Class[v0];
        }
        final char v = v-3.charAt(v-1);
        Class v2 = null;
        switch (v) {
            case 'Z': {
                final Class a1 = Boolean.TYPE;
                break;
            }
            case 'C': {
                final Class a2 = Character.TYPE;
                break;
            }
            case 'B': {
                final Class a3 = Byte.TYPE;
                break;
            }
            case 'S': {
                final Class a4 = Short.TYPE;
                break;
            }
            case 'I': {
                v2 = Integer.TYPE;
                break;
            }
            case 'J': {
                v2 = Long.TYPE;
                break;
            }
            case 'F': {
                v2 = Float.TYPE;
                break;
            }
            case 'D': {
                v2 = Double.TYPE;
                break;
            }
            case 'V': {
                v2 = Void.TYPE;
                break;
            }
            case 'L':
            case '[': {
                return getClassType(v-3, v-2, v-1, v0);
            }
            default: {
                return new Class[v0];
            }
        }
        final Class[] v3 = getType(v-3, v-2, v-1 + 1, v0 + 1);
        v3[v0] = v2;
        return v3;
    }
    
    private static Class[] getClassType(final String a3, final int a4, final int v1, final int v2) {
        int v3;
        for (v3 = v1; a3.charAt(v3) == '['; ++v3) {}
        if (a3.charAt(v3) == 'L') {
            v3 = a3.indexOf(59, v3);
            if (v3 < 0) {
                throw new IndexOutOfBoundsException("bad descriptor");
            }
        }
        String v4 = null;
        if (a3.charAt(v1) == 'L') {
            final String a5 = a3.substring(v1 + 1, v3);
        }
        else {
            v4 = a3.substring(v1, v3 + 1);
        }
        final Class[] v5 = getType(a3, a4, v3 + 1, v2 + 1);
        try {
            v5[v2] = getClassObject(v4.replace('/', '.'));
        }
        catch (ClassNotFoundException a6) {
            throw new RuntimeException(a6.getMessage());
        }
        return v5;
    }
    
    static {
        Desc.useContextClassLoader = false;
    }
}
