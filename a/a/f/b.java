package a.a.f;

import net.minecraft.util.*;
import com.backdoored.gui.*;
import com.backdoored.hacks.*;
import com.backdoored.setting.*;
import a.a.g.c.*;
import a.a.f.a.*;
import java.util.*;

public class b
{
    public static final ResourceLocation resourceLocation;
    public static Map<Category, a.a.f.a.b> gk;
    public static Map<BaseHack, c> gl;
    public static Map<Setting, e> gm;
    private static boolean gn;
    
    public b() {
        super();
    }
    
    public static void a() {
        if (b.gn) {
            return;
        }
        b.gn = true;
        for (final Category category : Category.c()) {
            b.gk.put(category, new a.a.f.a.b(category));
            for (final BaseHack baseHack : category.b()) {
                b.gl.put(baseHack, new c(baseHack));
            }
        }
        for (final Setting setting : d.a()) {
            if (setting.c() == a.a.g.c.b.yd || setting.c() == a.a.g.c.b.yf || setting.c() == a.a.g.c.b.ye) {
                b.gm.put(setting, new a.a.f.a.d(setting));
            }
            else {
                b.gm.put(setting, new e(setting));
            }
        }
    }
    
    public static BaseHack a(final a a) {
        for (final c c : c.d()) {
            if (c.fi.equals(a.fi)) {
                return c.a();
            }
        }
        return null;
    }
    
    public static a a(final int n, final int n2) {
        for (final a a : c()) {
            if (!a.fj) {
                continue;
            }
            if (n >= a.fe && n <= a.fe + a.fg && n2 >= a.ff && n2 <= a.ff + a.fh) {
                return a;
            }
        }
        return null;
    }
    
    public static ArrayList<a> b() {
        final ArrayList<a.a.f.a.b> list = (ArrayList<a.a.f.a.b>)new ArrayList<c>();
        for (final a.a.f.a.b b : a.a.f.a.b.c()) {
            list.add((c)b);
            for (final c c : b.b()) {
                list.add(c);
                list.addAll((Collection<? extends a>)c.c());
            }
        }
        return (ArrayList<a>)list;
    }
    
    public static ArrayList<a> c() {
        final ArrayList<a.a.f.a.b> list = (ArrayList<a.a.f.a.b>)new ArrayList<a>();
        for (final a.a.f.a.b b : a.a.f.a.b.d()) {
            list.add(b);
            list.addAll((Collection<? extends a>)b.b());
            final Iterator<c> iterator2 = b.b().iterator();
            while (iterator2.hasNext()) {
                list.addAll((Collection<? extends a>)iterator2.next().c());
            }
        }
        return (ArrayList<a>)list;
    }
    
    static {
        resourceLocation = new ResourceLocation("backdoored", "textures/white.png");
        b.gk = new HashMap<Category, a.a.f.a.b>();
        b.gl = new HashMap<BaseHack, c>();
        b.gm = new HashMap<Setting, e>();
        b.gn = false;
    }
}
