package a.a.g.c;

import java.util.*;
import com.backdoored.setting.*;

public enum b
{
    xz("BIND", 0), 
    ya("ENUM", 1), 
    yb("STRING", 2), 
    yc("BOOLEAN", 3), 
    yd("INTEGER", 4), 
    ye("DOUBLE", 5), 
    yf("FLOAT", 6);
    
    protected ArrayList<Setting> yg;
    private static final /* synthetic */ b[] $VALUES;
    
    public static b[] values() {
        return b.$VALUES.clone();
    }
    
    public static b valueOf(final String s) {
        return Enum.valueOf(b.class, s);
    }
    
    private b(final String s, final int n) {
        this.yg = new ArrayList<Setting>();
    }
    
    public ArrayList<Setting> a() {
        return this.yg;
    }
    
    static {
        $VALUES = new b[] { b.xz, b.ya, b.yb, b.yc, b.yd, b.ye, b.yf };
    }
}
