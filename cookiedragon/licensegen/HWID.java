package cookiedragon.licensegen;

import java.security.*;

public class HWID
{
    private static final char[] hexArray;
    
    public HWID() {
        super();
    }
    
    public static byte[] generateHWID() {
        try {
            final MessageDigest v1 = MessageDigest.getInstance("MD5");
            final String v2 = System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + Runtime.getRuntime().availableProcessors() + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS");
            return v1.digest(v2.getBytes());
        }
        catch (NoSuchAlgorithmException v3) {
            throw new Error("Algorithm wasn't found.", v3);
        }
    }
    
    public static byte[] hexStringToByteArray(final String v1) {
        final int v2 = v1.length();
        final byte[] v3 = new byte[v2 / 2];
        for (int a1 = 0; a1 < v2; a1 += 2) {
            v3[a1 / 2] = (byte)((Character.digit(v1.charAt(a1), 16) << 4) + Character.digit(v1.charAt(a1 + 1), 16));
        }
        return v3;
    }
    
    public static String bytesToHex(final byte[] v-1) {
        final char[] v0 = new char[v-1.length * 2];
        for (int v2 = 0; v2 < v-1.length; ++v2) {
            final int a1 = v-1[v2] & 0xFF;
            v0[v2 * 2] = HWID.hexArray[a1 >>> 4];
            v0[v2 * 2 + 1] = HWID.hexArray[a1 & 0xF];
        }
        return new String(v0);
    }
    
    static {
        hexArray = "0123456789ABCDEF".toCharArray();
    }
}
