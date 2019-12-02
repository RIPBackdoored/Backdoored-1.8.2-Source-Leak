package a.a.c;

import org.json.*;
import java.nio.charset.*;
import org.apache.commons.io.*;
import a.a.g.b.*;
import com.backdoored.hacks.*;
import a.a.g.c.*;
import com.backdoored.setting.*;
import a.a.f.a.*;
import com.backdoored.commands.*;
import com.backdoored.*;
import java.io.*;
import net.minecraftforge.fml.common.*;
import java.util.*;

@Deprecated
public class a
{
    private static final File bk;
    private static JSONObject bl;
    private static JSONObject bm;
    private static JSONObject bn;
    private static JSONObject bo;
    private static JSONObject bp;
    
    public a() {
        super();
    }
    
    public static void a() {
        a(a.bk);
    }
    
    public static void a(final File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            String a1 = FileUtils.readFileToString(file, Charset.defaultCharset());
            if (a1.trim().isEmpty() && file.renameTo(file)) {
                b();
                a1 = FileUtils.readFileToString(file, Charset.defaultCharset());
            }
            a.bl = new JSONObject(a1);
            a.bm = a.bl.getJSONObject("Hacks");
            a.bn = a.bl.getJSONObject("Category");
            a.bo = a.bl.getJSONObject("Commands");
            a.bp = a.bl.getJSONObject("Hud");
            for (final BaseHack baseHack : c.hacks()) {
                if (a(baseHack, "Enabled") != null) {
                    try {
                        baseHack.setEnabled((boolean)a(baseHack, "Enabled"));
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                for (final Setting<Object> setting : d.a(baseHack)) {
                    try {
                        if (a(baseHack, setting.a()) == null) {
                            continue;
                        }
                        final Object a2 = a(baseHack, setting.a());
                        if (a2 == null || setting.j() == null) {
                            continue;
                        }
                        final Object cast = setting.j().cast(a2);
                        if (cast == null) {
                            continue;
                        }
                        setting.a(cast);
                    }
                    catch (Exception ex2) {
                        ex2.printStackTrace();
                    }
                }
            }
            for (final b b : b.c()) {
                if (a(b, "x") != null) {
                    b.fe = (int)a(b, "x");
                }
                if (a(b, "y") != null) {
                    b.ff = (int)a(b, "y");
                }
                if (a(b, "open") != null) {
                    b.fd = (boolean)a(b, "open");
                }
            }
            if (a("prefix") != null) {
                Command.x = (String)a("prefix");
            }
            for (final a.a.h.a.a a3 : Backdoored.k.yx) {
                final Object c = c(a3.b(), "Visible");
                if (c instanceof Boolean) {
                    a3.a((boolean)c);
                }
                final Object c2 = c(a3.b(), "x");
                if (c2 instanceof Integer) {
                    a3.d().bco = (int)c2;
                }
                final Object c3 = c(a3.b(), "y");
                if (c3 instanceof Integer) {
                    a3.d().bcp = (int)c3;
                }
            }
        }
        catch (Exception ex3) {
            System.out.println("ERROR READING BACKDOORED CONFIG FILE!!!");
            ex3.printStackTrace();
        }
    }
    
    private static Object a(final BaseHack baseHack, final String s) {
        return a(baseHack.name, s);
    }
    
    private static Object a(final String a1, final String a2) {
        try {
            return a.bm.getJSONObject(a1).get(a2);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    private static Object a(final b b, final String s) {
        return b(b.fi, s);
    }
    
    private static Object b(final String a1, final String a2) {
        try {
            return a.bn.getJSONObject(a1).get(a2);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    private static Object a(final String a1) {
        try {
            return a.bo.get(a1);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    private static Object c(final String a1, final String a2) {
        try {
            return a.bp.getJSONObject(a1).get(a2);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static void b() {
        b(a.bk);
    }
    
    public static void b(final File file) {
        a.bl.put("Hacks", c());
        a.bl.put("Category", d());
        a.bl.put("Commands", e());
        a.bl.put("Hud", f());
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.renameTo(file)) {
                final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.print(a.bl.toString(4));
                printWriter.close();
                FMLLog.log.info(a.bl.toString());
            }
            else {
                System.out.println("Failed to save, file already in use");
            }
        }
        catch (Exception ex) {
            System.out.println("ERROR SAVING BACKDOORED CONFIG FILE!!!");
            ex.printStackTrace();
        }
    }
    
    private static JSONObject c() {
        for (final BaseHack baseHack : c.hacks()) {
            final JSONObject a2 = new JSONObject();
            a2.put("Enabled", baseHack.getEnabled());
            final ArrayList a3 = d.a(baseHack);
            if (a3 != null) {
                for (final Setting<Object> setting : a3) {
                    a2.put(setting.a(), setting.getValInt());
                }
            }
            a.bm.put(baseHack.name, a2);
        }
        return a.bm;
    }
    
    private static JSONObject d() {
        for (final b b : b.c()) {
            final JSONObject a2 = new JSONObject();
            a2.put("x", b.fe);
            a2.put("y", b.ff);
            a2.put("open", b.fd);
            a.bn.put(b.fi, a2);
        }
        return a.bn;
    }
    
    private static JSONObject e() {
        a.bo.put("prefix", Command.x);
        return a.bo;
    }
    
    private static JSONObject f() {
        for (final a.a.h.a.a a : Backdoored.k.yx) {
            final JSONObject a2 = new JSONObject();
            a2.put("Visible", (Object)a.c());
            a2.put("x", (Object)a.d().bco);
            a2.put("y", (Object)a.d().bcp);
            a.bp.put(a.b(), a2);
        }
        return a.bp;
    }
    
    static {
        bk = new File("Backdoored/config.json");
        a.bl = new JSONObject();
        a.bm = new JSONObject();
        a.bn = new JSONObject();
        a.bo = new JSONObject();
        a.bp = new JSONObject();
    }
}
