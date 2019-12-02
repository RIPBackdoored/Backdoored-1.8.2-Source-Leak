package org.spongepowered.asm.util;

public abstract class ObfuscationUtil
{
    private ObfuscationUtil() {
        super();
    }
    
    public static String mapDescriptor(final String a1, final IClassRemapper a2) {
        return remapDescriptor(a1, a2, false);
    }
    
    public static String unmapDescriptor(final String a1, final IClassRemapper a2) {
        return remapDescriptor(a1, a2, true);
    }
    
    private static String remapDescriptor(final String a3, final IClassRemapper v1, final boolean v2) {
        final StringBuilder v3 = new StringBuilder();
        StringBuilder v4 = null;
        for (int a4 = 0; a4 < a3.length(); ++a4) {
            final char a5 = a3.charAt(a4);
            if (v4 != null) {
                if (a5 == ';') {
                    v3.append('L').append(remap(v4.toString(), v1, v2)).append(';');
                    v4 = null;
                }
                else {
                    v4.append(a5);
                }
            }
            else if (a5 == 'L') {
                v4 = new StringBuilder();
            }
            else {
                v3.append(a5);
            }
        }
        if (v4 != null) {
            throw new IllegalArgumentException("Invalid descriptor '" + a3 + "', missing ';'");
        }
        return v3.toString();
    }
    
    private static Object remap(final String a1, final IClassRemapper a2, final boolean a3) {
        final String v1 = a3 ? a2.unmap(a1) : a2.map(a1);
        return (v1 != null) ? v1 : a1;
    }
    
    public interface IClassRemapper
    {
        String map(final String p0);
        
        String unmap(final String p0);
    }
}
