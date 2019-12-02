package a.a.f.a;

import java.util.*;
import com.backdoored.hacks.*;

public class c extends a
{
    private static ArrayList<c> fy;
    private ArrayList<e> fz;
    private b ga;
    private BaseHack gb;
    
    public c(final BaseHack gb) {
        super(a.a.f.b.gk.get(gb.category).fe, a.a.f.b.gk.get(gb.category).ff + 20 * a.a.f.b.gk.get(gb.category).fu, 100, 20, gb.name, true, new float[] { 0.2f, 0.0f, 0.9f, 1.0f });
        this.fz = new ArrayList<e>();
        final b b = a.a.f.b.gk.get(gb.category);
        ++b.fu;
        this.ga = a.a.f.b.gk.get(gb.category);
        this.gb = gb;
        c.fy.add(this);
        this.ga.b().add(this);
    }
    
    public BaseHack a() {
        return this.gb;
    }
    
    public b b() {
        return this.ga;
    }
    
    public ArrayList<e> c() {
        return this.fz;
    }
    
    public static ArrayList<c> d() {
        return c.fy;
    }
    
    @Override
    public String toString() {
        return this.fi;
    }
    
    static {
        c.fy = new ArrayList<c>();
    }
}
