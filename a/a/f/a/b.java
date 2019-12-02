package a.a.f.a;

import java.util.*;
import com.backdoored.gui.*;

public class b extends a
{
    private static ArrayList<b> fn;
    private ArrayList<c> fo;
    public static final int fp = 100;
    public static final int fq = 20;
    private static final int fr = 25;
    private static int fs;
    private Category ft;
    public int fu;
    public boolean fv;
    public int fw;
    public int fx;
    
    public b(final Category ft) {
        super(25, b.fs, 100, 20, ft.a(), true, new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
        this.fo = new ArrayList<c>();
        this.fu = 1;
        this.fv = false;
        this.fw = 0;
        this.fx = 0;
        this.ft = ft;
        b.fs += 21;
        b.fn.add(this);
    }
    
    public Category a() {
        return this.ft;
    }
    
    public ArrayList<c> b() {
        return this.fo;
    }
    
    public static ArrayList<b> c() {
        return b.fn;
    }
    
    public static ArrayList<b> d() {
        final ArrayList<b> list = new ArrayList<b>();
        for (int i = c().size() - 1; i >= 0; --i) {
            list.add(c().get(i));
        }
        return list;
    }
    
    static {
        b.fn = new ArrayList<b>();
        b.fs = 25;
    }
}
