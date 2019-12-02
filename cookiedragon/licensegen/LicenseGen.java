package cookiedragon.licensegen;

import java.util.*;
import java.nio.charset.*;
import java.security.*;

public class LicenseGen
{
    public final String hwid;
    private final long seed;
    private Random generator;
    private static final String[] HASHES;
    
    public LicenseGen(final long a1) {
        super();
        this.seed = a1;
        this.generator = new Random(this.seed);
        this.hwid = this.getHwid();
    }
    
    public LicenseGen(final long a1, final String a2) {
        super();
        this.seed = a1;
        this.generator = new Random(this.seed);
        this.hwid = a2;
    }
    
    private String getHwid() {
        return HWID.bytesToHex(HWID.generateHWID());
    }
    
    public String genLicense() {
        try {
            String s = Long.toString(this.seed) + this.hwid;
            for (int n = this.generator.nextInt(20), v0 = 1; v0 <= n; ++v0) {
                s = this.hash(s.getBytes(), this.randomAlgorithm());
                if (this.generator.nextBoolean()) {
                    final byte[] v2 = new byte[7];
                    this.generator.nextBytes(v2);
                    s += new String(v2, Charset.forName("UTF-8"));
                }
            }
            for (int n = this.generator.nextInt(3), v0 = 1; v0 <= n; ++v0) {
                if (this.generator.nextBoolean()) {
                    s += this.hash(s.getBytes(), this.randomAlgorithm());
                }
                else {
                    s = this.hash(s.getBytes(), this.randomAlgorithm()) + s;
                }
            }
            s = this.hash((s + s + s).getBytes(), this.randomAlgorithm());
            return s;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private String hash(final byte[] v2, final String v3) {
        try {
            final MessageDigest a1 = MessageDigest.getInstance("SHA-256");
            a1.update(v2);
            return HWID.bytesToHex(a1.digest());
        }
        catch (Exception a2) {
            a2.printStackTrace();
            return "";
        }
    }
    
    private String randomAlgorithm() {
        final int v1 = this.generator.nextInt(LicenseGen.HASHES.length);
        return LicenseGen.HASHES[v1];
    }
    
    static {
        HASHES = new String[] { "MD2", "MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512" };
    }
}
