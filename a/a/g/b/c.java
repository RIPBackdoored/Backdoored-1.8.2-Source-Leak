package a.a.g.b;

import com.backdoored.hacks.*;
import com.backdoored.natives.*;
import com.backdoored.gui.*;
import java.util.*;

public class c
{
    private static List<BaseHack> hacks;
    public static Map<String, BaseHack> hacksMap;
    private static Boolean isAlpha;
    public static d qe;
    public static a qf;
    
    public c() {
        super();
    }
    
    public static List<b> hacks() {
        return (List<b>)c.hacks;
    }
    
    public static BaseHack a(final String s) {
        for (final BaseHack baseHack : c.hacks) {
            if (baseHack.name.equalsIgnoreCase(s)) {
                return baseHack;
            }
        }
        throw new RuntimeException(EncryptedStringPool.poolGet(10) + s + EncryptedStringPool.poolGet(11));
    }
    
    public static b a(final Class<? extends b> clazz) {
        for (final BaseHack baseHack : c.hacks) {
            if (baseHack.getClass() == clazz) {
                return (b)baseHack;
            }
        }
        throw new RuntimeException(EncryptedStringPool.poolGet(12) + clazz.getName() + EncryptedStringPool.poolGet(11));
    }
    
    public static HashMap<Category, List<b>> b() {
        final HashMap<Category, ArrayList<BaseHack>> hashMap = (HashMap<Category, ArrayList<BaseHack>>)new HashMap<Category, List<BaseHack>>();
        for (final BaseHack baseHack : c.hacks) {
            if (hashMap.containsKey(baseHack.category)) {
                hashMap.get(baseHack.category).add(baseHack);
            }
            else {
                hashMap.put(baseHack.category, new ArrayList<BaseHack>(Arrays.asList(baseHack)));
            }
        }
        return (HashMap<Category, List<b>>)hashMap;
    }
    
    public static void a(final boolean b) {
        if (b) {
            if (c.isAlpha == null || !c.isAlpha) {
                c.hacks.sort((Comparator<? super BaseHack>)c.qf);
                c.isAlpha = true;
            }
        }
        else if (c.isAlpha == null || c.isAlpha) {
            c.hacks.sort((Comparator<? super BaseHack>)c.qe);
            c.isAlpha = false;
        }
    }
    
    static {
        c.hacks = (List<BaseHack>)new ArrayList<b>() {
            public c$a() {
                super();
            }
        };
        c.hacksMap = new HashMap<String, BaseHack>();
        c.isAlpha = null;
        c.qe = new d();
        c.qf = new a();
    }
}
